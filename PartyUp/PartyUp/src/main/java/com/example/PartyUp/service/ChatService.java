package com.example.PartyUp.service;

import com.example.PartyUp.model.entity.Mensaje;
import com.example.PartyUp.model.entity.Usuario;
import com.example.PartyUp.repository.MensajeRepository;
import com.example.PartyUp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CompatibilidadService compatibilidadService;

    public Mensaje enviarMensaje(String remitenteId, String destinatarioId, String contenido) {
        // Verificar compatibilidad
        if (!compatibilidadService.sonCompatibles(remitenteId, destinatarioId)) {
            throw new RuntimeException("Los usuarios no son compatibles para chatear");
        }

        Optional<Usuario> remitenteOpt = usuarioRepository.findById(remitenteId);
        Optional<Usuario> destinatarioOpt = usuarioRepository.findById(destinatarioId);

        if (remitenteOpt.isEmpty() || destinatarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Mensaje mensaje = new Mensaje(contenido, remitenteOpt.get(), destinatarioOpt.get());
        return mensajeRepository.save(mensaje);
    }

    public List<Mensaje> obtenerConversacion(String usuario1Id, String usuario2Id) {
        return mensajeRepository.findConversacion(usuario1Id, usuario2Id);
    }

    public void eliminarConversacion(String usuario1Id, String usuario2Id) {
        Optional<Usuario> usuario1Opt = usuarioRepository.findById(usuario1Id);
        Optional<Usuario> usuario2Opt = usuarioRepository.findById(usuario2Id);

        if (usuario1Opt.isPresent() && usuario2Opt.isPresent()) {
            mensajeRepository.deleteByRemitenteAndDestinatarioOrRemitenteAndDestinatario(
                usuario1Opt.get(), usuario2Opt.get(), usuario2Opt.get(), usuario1Opt.get());
        }
    }

    public List<Mensaje> obtenerMensajesNoLeidos(String usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        return usuarioOpt.map(usuario -> mensajeRepository.findByDestinatarioAndLeidoFalse(usuario))
                       .orElse(List.of());
    }

    public void marcarComoLeido(String mensajeId) {
        Optional<Mensaje> mensajeOpt = mensajeRepository.findById(mensajeId);
        mensajeOpt.ifPresent(mensaje -> {
            mensaje.setLeido(true);
            mensajeRepository.save(mensaje);
        });
    }
}