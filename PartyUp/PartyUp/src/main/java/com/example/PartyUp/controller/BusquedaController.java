package com.example.PartyUp.controller;

import com.example.PartyUp.model.dto.BusquedaFiltrosDTO;
import com.example.PartyUp.model.dto.SugerenciaDTO;
import com.example.PartyUp.service.BusquedaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/busqueda")
@CrossOrigin(origins = "*")
public class BusquedaController {

    @Autowired
    private BusquedaService busquedaService;

    @PostMapping("/filtrar/{usuarioId}")
    public ResponseEntity<List<SugerenciaDTO>> buscarUsuariosConFiltros(
            @PathVariable String usuarioId,
            @RequestBody BusquedaFiltrosDTO filtros) {
        try {
            List<SugerenciaDTO> resultados = busquedaService.buscarUsuariosConFiltros(usuarioId, filtros);
            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/ciudades")
    public ResponseEntity<List<String>> obtenerCiudadesDisponibles() {
        try {
            List<String> ciudades = busquedaService.obtenerCiudadesDisponibles();
            return ResponseEntity.ok(ciudades);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}