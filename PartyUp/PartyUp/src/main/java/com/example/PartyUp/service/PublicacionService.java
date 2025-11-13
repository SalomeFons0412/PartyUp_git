package com.example.PartyUp.service;

import com.example.PartyUp.model.entity.Publicacion;
import com.example.PartyUp.model.entity.Usuario;
import com.example.PartyUp.repository.PublicacionRepository;
import com.example.PartyUp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Publicacion crearPublicacion(String usuarioId, String contenido, String tipo, String urlMedia) {
        Usuario autor = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Publicacion p = new Publicacion();
        p.setAutorId(autor.getId());
        p.setAutorNombre(autor.getNombre());
        p.setContenido(contenido);
        p.setTipo(tipo);
        p.setUrlMedia(urlMedia);
        p.setFecha(LocalDateTime.now());

        return publicacionRepository.save(p);
    }

    public List<Publicacion> obtenerPublicacionesRecientes() {
        return publicacionRepository.findAllByOrderByFechaDesc();
    }
}
