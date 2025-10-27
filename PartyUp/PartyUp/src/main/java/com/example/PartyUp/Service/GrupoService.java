package com.example.PartyUp.service;

import com.example.PartyUp.model.entity.Grupo;
import com.example.PartyUp.repository.GrupoRepository;
import com.example.PartyUp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear grupo
    public Grupo crearGrupo(String creadorId, String nombre, String descripcion) {
        // Verificar que el creador exista
        usuarioRepository.findById(creadorId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear grupo
        Grupo grupo = new Grupo(nombre, descripcion, creadorId);
        return grupoRepository.save(grupo);
    }

    // Invitar miembro
    public Grupo invitarMiembro(String grupoId, String usuarioId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        grupo.agregarMiembro(usuarioId);
        return grupoRepository.save(grupo);
    }

    // Expulsar miembro
    public Grupo expulsarMiembro(String grupoId, String usuarioId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        grupo.expulsarMiembro(usuarioId);
        return grupoRepository.save(grupo);
    }

    // Abandonar grupo
    public void abandonarGrupo(String grupoId, String usuarioId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        grupo.expulsarMiembro(usuarioId);
        grupoRepository.save(grupo);
    }

    // Eliminar grupo
    public void eliminarGrupo(String grupoId) {
        grupoRepository.deleteById(grupoId);
    }
}
