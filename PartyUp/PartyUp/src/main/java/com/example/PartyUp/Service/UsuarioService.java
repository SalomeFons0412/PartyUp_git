package com.example.PartyUp.Service;

import com.example.PartyUp.Model.Entity.Usuario;
import com.example.PartyUp.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Crear un nuevo usuario
    public Usuario crearUsuario(Usuario usuario) {
        // Validar si ya existe un usuario con el mismo correo
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // Encriptar la contraseña antes de guardar
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        return usuarioRepository.save(usuario);
    }

    // Actualizar usuario existente
    public Usuario actualizarUsuario(String id, Usuario nuevosDatos) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    if (nuevosDatos.getNombre() != null && !nuevosDatos.getNombre().isBlank()) {
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
}
