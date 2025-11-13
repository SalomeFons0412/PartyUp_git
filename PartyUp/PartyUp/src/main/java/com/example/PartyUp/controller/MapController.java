package com.example.PartyUp.controller;

import com.example.PartyUp.model.dto.UsuarioMapaDTO;
import com.example.PartyUp.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/map")
@CrossOrigin(origins = "*")
public class MapController {

    @Autowired
    private MapService mapService;

    @GetMapping("/nearby")
    public ResponseEntity<List<UsuarioMapaDTO>> obtenerCercanos(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(required = false, defaultValue = "5") double radiusKm) {
        try {
            List<UsuarioMapaDTO> lista = mapService.obtenerUsuariosCercanos(lat, lon, radiusKm);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
