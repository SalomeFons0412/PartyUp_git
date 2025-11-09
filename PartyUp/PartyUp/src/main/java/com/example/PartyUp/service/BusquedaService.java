package com.example.PartyUp.service;

import com.example.PartyUp.model.dto.BusquedaFiltrosDTO;
import com.example.PartyUp.model.dto.SugerenciaDTO;
import com.example.PartyUp.model.entity.Usuario;
import com.example.PartyUp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusquedaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CompatibilidadService compatibilidadService;

    public List<SugerenciaDTO> buscarUsuariosConFiltros(String usuarioId, BusquedaFiltrosDTO filtros) {
        Optional<Usuario> usuarioActualOpt = usuarioRepository.findById(usuarioId);
        if (usuarioActualOpt.isEmpty()) {
            return new ArrayList<>();
        }

        Usuario usuarioActual = usuarioActualOpt.get();
        List<Usuario> todosUsuarios = usuarioRepository.findAll();

        // Aplicar filtros
        List<Usuario> usuariosFiltrados = todosUsuarios.stream()
                .filter(usuario -> !usuario.getId().equals(usuarioId)) // Excluir usuario actual
                .filter(usuario -> filtrarPorCiudad(usuario, filtros.getCiudad()))
                .filter(usuario -> filtrarPorPlataforma(usuario, filtros.getPlataforma()))
                .filter(usuario -> filtrarPorJuego(usuario, filtros.getJuego()))
                .filter(usuario -> filtrarPorDistancia(usuario, usuarioActual, filtros.getDistanciaMaxima()))
                .collect(Collectors.toList());

        // Convertir a SugerenciaDTO con compatibilidad
        List<SugerenciaDTO> resultados = new ArrayList<>();
        for (Usuario usuario : usuariosFiltrados) {
            double compatibilidad = compatibilidadService.calcularCompatibilidad(usuarioActual, usuario);
            
            SugerenciaDTO sugerencia = new SugerenciaDTO();
            sugerencia.setUsuarioId(usuario.getId());
            sugerencia.setNombre(usuario.getNombre());
            sugerencia.setCorreo(usuario.getCorreo());
            sugerencia.setPorcentajeCompatibilidad(compatibilidad);
            sugerencia.setJuegosComunes(compatibilidadService.obtenerJuegosComunes(usuarioActual.getJuegos(), usuario.getJuegos()));
            sugerencia.setPlataformasComunes(compatibilidadService.obtenerPlataformasComunes(usuarioActual.getPlataformas(), usuario.getPlataformas()));
            sugerencia.setCiudad(usuario.getCiudad());
            sugerencia.setDistancia(calcularDistancia(usuarioActual, usuario));
            
            resultados.add(sugerencia);
        }

        // Ordenar por compatibilidad descendente
        resultados.sort((s1, s2) -> Double.compare(s2.getPorcentajeCompatibilidad(), s1.getPorcentajeCompatibilidad()));

        return resultados;
    }

    //MÉTODO PARA OBTENER CIUDADES DISPONIBLES
    public List<String> obtenerCiudadesDisponibles() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(Usuario::getCiudad)
                .filter(ciudad -> ciudad != null && !ciudad.isEmpty())
                .distinct()
                .collect(Collectors.toList());
    }

    private boolean filtrarPorCiudad(Usuario usuario, String ciudad) {
        if (ciudad == null || ciudad.isEmpty()) {
            return true; // No filtrar si no se especifica ciudad
        }
        return ciudad.equalsIgnoreCase(usuario.getCiudad());
    }

    private boolean filtrarPorPlataforma(Usuario usuario, String plataforma) {
        if (plataforma == null || plataforma.isEmpty()) {
            return true; // No filtrar si no se especifica plataforma
        }
        return usuario.getPlataformas() != null && 
               usuario.getPlataformas().stream()
                      .anyMatch(p -> p.equalsIgnoreCase(plataforma));
    }

    private boolean filtrarPorJuego(Usuario usuario, String juego) {
        if (juego == null || juego.isEmpty()) {
            return true; // No filtrar si no se especifica juego
        }
        return usuario.getJuegos() != null && 
               usuario.getJuegos().stream()
                      .anyMatch(j -> j.equalsIgnoreCase(juego));
    }

    private boolean filtrarPorDistancia(Usuario usuario, Usuario usuarioActual, Double distanciaMaxima) {
        if (distanciaMaxima == null || distanciaMaxima <= 0) {
            return true; // No filtrar si no se especifica distancia
        }
        
        if (usuario.getLatitud() == null || usuario.getLongitud() == null ||
            usuarioActual.getLatitud() == null || usuarioActual.getLongitud() == null) {
            return true; // No filtrar si falta información de ubicación
        }

        double distancia = calcularDistancia(usuarioActual, usuario);
        return distancia <= distanciaMaxima;
    }

    private double calcularDistancia(Usuario usuario1, Usuario usuario2) {
        if (usuario1.getLatitud() == null || usuario1.getLongitud() == null ||
            usuario2.getLatitud() == null || usuario2.getLongitud() == null) {
            return 0.0;
        }

        // Fórmula de Haversine para calcular distancia entre dos coordenadas
        double radioTierra = 6371; // Radio de la Tierra en km

        double difLatitud = Math.toRadians(usuario2.getLatitud() - usuario1.getLatitud());
        double difLongitud = Math.toRadians(usuario2.getLongitud() - usuario1.getLongitud());

        double a = Math.sin(difLatitud / 2) * Math.sin(difLatitud / 2) +
                   Math.cos(Math.toRadians(usuario1.getLatitud())) * Math.cos(Math.toRadians(usuario2.getLatitud())) *
                   Math.sin(difLongitud / 2) * Math.sin(difLongitud / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return radioTierra * c; // Distancia en km
    }
}