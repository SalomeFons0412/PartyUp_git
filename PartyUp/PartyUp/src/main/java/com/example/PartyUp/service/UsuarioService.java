package com.example.PartyUp.service;

import com.example.PartyUp.model.entity.Usuario;
import com.example.PartyUp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario actualizarUsuario(String id, Usuario nuevosDatos) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    if (nuevosDatos.getNombre() != null && !((String) nuevosDatos.getNombre()).isBlank()) {
                        usuario.setNombre(nuevosDatos.getNombre());
                    }
                    if (nuevosDatos.getCorreo() != null && !nuevosDatos.getCorreo().isBlank()) {
                        usuario.setCorreo(nuevosDatos.getCorreo());
                    }
                    if (nuevosDatos.getTelefono() != null && !nuevosDatos.getTelefono().isBlank()) {
                        usuario.setTelefono(nuevosDatos.getTelefono());
                    }
                    if (nuevosDatos.getContrasena() != null && !nuevosDatos.getContrasena().isBlank()) {
                        usuario.setContrasena(passwordEncoder.encode(nuevosDatos.getContrasena()));
                    }
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    public Usuario crearUsuario(Usuario nuevoUsuario) {
    // Verificar si ya existe un usuario con el mismo correo
    if (usuarioRepository.findByCorreo(nuevoUsuario.getCorreo()).isPresent()) {
        throw new RuntimeException("El correo ya está registrado");
    }

    // Encriptar la contraseña antes de guardar
    nuevoUsuario.setContrasena(passwordEncoder.encode(nuevoUsuario.getContrasena()));

    // Guardar usuario en la base de datos
    return usuarioRepository.save(nuevoUsuario);
    }


    //actualizar solo preferencias
    public Usuario actualizarPreferencias(String usuarioId, List<String> juegos, List<String> plataformas, String horarios) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setJuegos(juegos);
            usuario.setPlataformas(plataformas);
            usuario.setHorarios(horarios);
            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("Usuario no encontrado");
    }

    //método para obtener usuario por ID
    public Optional<Usuario> obtenerUsuarioPorId(String id) {
        return usuarioRepository.findById(id);
    }

    // método para obtener todos los usuarios
    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario actualizarMostrarUbicacion(String usuarioId, boolean mostrar) {
    Usuario usuario = usuarioRepository.findById(usuarioId)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
    usuario.setMostrarUbicacion(mostrar);
    return usuarioRepository.save(usuario);
    }   


}
