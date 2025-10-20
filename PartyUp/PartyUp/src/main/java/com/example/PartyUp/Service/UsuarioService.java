package com.example.PartyUp.Service;

import com.example.PartyUp.Model.Entity.Usuario;
import com.example.PartyUp.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    //metodo para encriptar
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario actualizarUsuario(String id, Usuario nuevosDatos) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    // Validar que los campos no estén vacíos antes de actualizar
                    if (nuevosDatos.getNombre() != null && !nuevosDatos.getNombre().isBlank()) {
                        usuario.setNombre(nuevosDatos.getNombre());
                    }
                    if (nuevosDatos.getCorreo() != null && !nuevosDatos.getCorreo().isBlank()) {
                        usuario.setCorreo(nuevosDatos.getCorreo());
                    }
                    if (nuevosDatos.getTelefono() != null && !nuevosDatos.getTelefono().isBlank()) {
                        usuario.setTelefono(nuevosDatos.getTelefono());
                    }
                    // Encriptar la contraseña antes de actualizar
                    if (nuevosDatos.getContrasena() != null && !nuevosDatos.getContrasena().isBlank()) {
                        usuario.setContrasena(passwordEncoder.encode(nuevosDatos.getContrasena()));
                    }
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
