package com.example.PartyUp.service;

import com.example.PartyUp.model.dto.UsuarioMapaDTO;
import com.example.PartyUp.model.entity.Usuario;
import com.example.PartyUp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioMapaDTO> obtenerUsuariosCercanos(double lat, double lon, double radiusKm) {
        // Solo obtenemos los usuarios que tienen mostrarUbicacion = true
        List<Usuario> usuarios = usuarioRepository.findByMostrarUbicacionTrue();
        List<UsuarioMapaDTO> cercanos = new ArrayList<>();

        for (Usuario u : usuarios) {
            if (u.getLatitud() == null || u.getLongitud() == null) continue;

            double dist = distanciaKm(lat, lon, u.getLatitud(), u.getLongitud());
            if (dist <= radiusKm) {
                UsuarioMapaDTO dto = new UsuarioMapaDTO();
                dto.setId(u.getId());
                dto.setNombre(u.getNombre());
                dto.setLatitud(u.getLatitud());
                dto.setLongitud(u.getLongitud());
                dto.setCiudad(u.getCiudad());
                dto.setDistanciaKm(dist);
                cercanos.add(dto);
            }
        }
        return cercanos;
    }

    // para calcular distancia entre dos coordenadas (en km)
    private double distanciaKm(double lat1, double lon1, double lat2, double lon2) {
        double radioTierra = 6371; // km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return radioTierra * c;
    }
}
