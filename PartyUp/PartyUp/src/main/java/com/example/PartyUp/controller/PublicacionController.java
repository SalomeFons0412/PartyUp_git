package com.example.PartyUp.controller;

import com.example.PartyUp.model.entity.Publicacion;
import com.example.PartyUp.service.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @PostMapping
    public ResponseEntity<Publicacion> crearPublicacion(@RequestBody Map<String, String> body) {
        String usuarioId = body.get("usuarioId");
        String contenido = body.get("contenido");
        String tipo = body.get("tipo"); // "texto", "imagen" o "video"
        String urlMedia = body.get("urlMedia"); // opcional

        Publicacion nueva = publicacionService.crearPublicacion(usuarioId, contenido, tipo, urlMedia);
        return ResponseEntity.ok(nueva);
    }

    @GetMapping("/recientes")
    public ResponseEntity<List<Publicacion>> obtenerRecientes() {
        List<Publicacion> publicaciones = publicacionService.obtenerPublicacionesRecientes();
        return ResponseEntity.ok(publicaciones);
    }
}
