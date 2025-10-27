package com.example.PartyUp.service;

import com.example.PartyUp.model.entity.Usuario;
import com.example.PartyUp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

}
