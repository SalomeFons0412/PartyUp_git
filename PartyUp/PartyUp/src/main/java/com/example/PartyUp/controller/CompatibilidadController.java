package com.example.PartyUp.controller;

import com.example.PartyUp.model.dto.SugerenciaDTO;
import com.example.PartyUp.service.CompatibilidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compatibilidad")
public class CompatibilidadController {
    
    @Autowired
    private CompatibilidadService compatibilidadService;
    
    @GetMapping("/sugerencias/{usuarioId}")
    public ResponseEntity<List<SugerenciaDTO>> obtenerSugerencias(@PathVariable String usuarioId) {
        List<SugerenciaDTO> sugerencias = compatibilidadService.obtenerSugerenciasCompatibles(usuarioId);
        return ResponseEntity.ok(sugerencias);
    }
}