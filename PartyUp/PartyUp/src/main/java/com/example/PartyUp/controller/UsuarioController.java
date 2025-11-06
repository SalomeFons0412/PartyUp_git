package com.example.PartyUp.controller;

import com.example.PartyUp.model.entity.Usuario;
import com.example.PartyUp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(
            @PathVariable String id,
            @RequestBody Usuario usuario) {

        Usuario actualizado = usuarioService.actualizarUsuario(id, usuario);
        return ResponseEntity.ok(actualizado);
    }

    //endpoint para preferencias
    @PutMapping("/{usuarioId}/preferencias")
    public ResponseEntity<Usuario> actualizarPreferencias(
            @PathVariable String usuarioId,
            @RequestBody PreferenciasRequest request) {
        
        try {
            Usuario usuarioActualizado = usuarioService.actualizarPreferencias(
                usuarioId, 
                request.getJuegos(), 
                request.getPlataformas(), 
                request.getHorarios()
            );
            return ResponseEntity.ok(usuarioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //request de preferencias
    public static class PreferenciasRequest {
        private List<String> juegos;
        private List<String> plataformas;
        private String horarios;
        
        public List<String> getJuegos() { return juegos; }
        public void setJuegos(List<String> juegos) { this.juegos = juegos; }
        
        public List<String> getPlataformas() { return plataformas; }
        public void setPlataformas(List<String> plataformas) { this.plataformas = plataformas; }
        
        public String getHorarios() { return horarios; }
        public void setHorarios(String horarios) { this.horarios = horarios; }
    }
}