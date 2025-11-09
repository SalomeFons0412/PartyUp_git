package com.example.PartyUp.controller;

import com.example.PartyUp.model.entity.Mensaje;
import com.example.PartyUp.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/enviar")
    public ResponseEntity<Mensaje> enviarMensaje(
            @RequestParam String remitenteId,
            @RequestParam String destinatarioId,
            @RequestParam String contenido) {
        try {
            Mensaje mensaje = chatService.enviarMensaje(remitenteId, destinatarioId, contenido);
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/conversacion")
    public ResponseEntity<List<Mensaje>> obtenerConversacion(
            @RequestParam String usuario1Id,
            @RequestParam String usuario2Id) {
        try {
            List<Mensaje> mensajes = chatService.obtenerConversacion(usuario1Id, usuario2Id);
            return ResponseEntity.ok(mensajes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/conversacion")
    public ResponseEntity<Void> eliminarConversacion(
            @RequestParam String usuario1Id,
            @RequestParam String usuario2Id) {
        try {
            chatService.eliminarConversacion(usuario1Id, usuario2Id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/marcar-leido/{mensajeId}")
    public ResponseEntity<Void> marcarComoLeido(@PathVariable String mensajeId) {
        try {
            chatService.marcarComoLeido(mensajeId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/no-leidos/{usuarioId}")
    public ResponseEntity<List<Mensaje>> obtenerMensajesNoLeidos(@PathVariable String usuarioId) {
        try {
            List<Mensaje> mensajes = chatService.obtenerMensajesNoLeidos(usuarioId);
            return ResponseEntity.ok(mensajes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}