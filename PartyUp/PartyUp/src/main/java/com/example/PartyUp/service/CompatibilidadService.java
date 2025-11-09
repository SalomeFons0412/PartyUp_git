package com.example.PartyUp.service;

import com.example.PartyUp.model.dto.SugerenciaDTO;
import com.example.PartyUp.model.entity.Usuario;
import com.example.PartyUp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompatibilidadService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean sonCompatibles(String usuario1Id, String usuario2Id) {
        // Evitar que un usuario chatee consigo mismo
        if (usuario1Id.equals(usuario2Id)) {
            return false;
        }
        
        Optional<Usuario> usuario1Opt = usuarioRepository.findById(usuario1Id);
        Optional<Usuario> usuario2Opt = usuarioRepository.findById(usuario2Id);
        
        if (usuario1Opt.isEmpty() || usuario2Opt.isEmpty()) {
            return false;
        }
        
        Usuario usuario1 = usuario1Opt.get();
        Usuario usuario2 = usuario2Opt.get();
        
        double compatibilidad = calcularCompatibilidad(usuario1, usuario2);
        
        // Definir un umbral mínimo de compatibilidad (ej: 20%)
        double umbralMinimo = 20.0;
        
        return compatibilidad >= umbralMinimo;
    }
    
    public List<SugerenciaDTO> obtenerSugerenciasCompatibles(String usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            return new ArrayList<>();
        }
        
        Usuario usuarioActual = usuarioOpt.get();
        List<Usuario> todosUsuarios = usuarioRepository.findAll();
        
        List<SugerenciaDTO> sugerencias = new ArrayList<>();
        
        for (Usuario otroUsuario : todosUsuarios) {
            if (otroUsuario.getId().equals(usuarioId)) {
                continue; // Saltar el usuario actual
            }
            
            double compatibilidad = calcularCompatibilidad(usuarioActual, otroUsuario);
            
            if (compatibilidad > 0) {
                SugerenciaDTO sugerencia = new SugerenciaDTO();
                sugerencia.setUsuarioId(otroUsuario.getId());
                sugerencia.setNombre(otroUsuario.getNombre());
                sugerencia.setCorreo(otroUsuario.getCorreo()); 
                sugerencia.setPorcentajeCompatibilidad(compatibilidad);
                sugerencia.setJuegosComunes(obtenerJuegosComunes(usuarioActual.getJuegos(), otroUsuario.getJuegos()));
                sugerencia.setPlataformasComunes(obtenerPlataformasComunes(usuarioActual.getPlataformas(), otroUsuario.getPlataformas()));
                
                sugerencias.add(sugerencia);
            }
        }
        
        // Ordenar por compatibilidad descendente
        sugerencias.sort((s1, s2) -> Double.compare(s2.getPorcentajeCompatibilidad(), s1.getPorcentajeCompatibilidad()));
        
        return sugerencias;
    }
    
    public double calcularCompatibilidad(Usuario usuario1, Usuario usuario2) {
        double puntos = 0;
        
        // Comparar juegos (40%)
        if (usuario1.getJuegos() != null && usuario2.getJuegos() != null && !usuario1.getJuegos().isEmpty() && !usuario2.getJuegos().isEmpty()) {
            Set<String> juegos1 = new HashSet<>(usuario1.getJuegos());
            Set<String> juegos2 = new HashSet<>(usuario2.getJuegos());
            double compatJuegos = calcularCoeficienteJaccard(juegos1, juegos2);
            puntos += compatJuegos * 40;
        }
        
        // Comparar plataformas (30%)
        if (usuario1.getPlataformas() != null && usuario2.getPlataformas() != null && !usuario1.getPlataformas().isEmpty() && !usuario2.getPlataformas().isEmpty()) {
            Set<String> plataformas1 = new HashSet<>(usuario1.getPlataformas());
            Set<String> plataformas2 = new HashSet<>(usuario2.getPlataformas());
            double compatPlataformas = calcularCoeficienteJaccard(plataformas1, plataformas2);
            puntos += compatPlataformas * 30;
        }
        
        // Comparar horarios (30%)
        if (usuario1.getHorarios() != null && usuario2.getHorarios() != null) {
            double compatHorarios = calcularCompatibilidadHorarios(usuario1.getHorarios(), usuario2.getHorarios());
            puntos += compatHorarios * 30;
        }
        
        return Math.round(puntos * 100.0) / 100.0; // Redondear a 2 decimales
    }
    
    public List<String> obtenerJuegosComunes(List<String> juegos1, List<String> juegos2) {
        if (juegos1 == null || juegos2 == null) return new ArrayList<>();
        
        Set<String> set1 = new HashSet<>(juegos1);
        Set<String> set2 = new HashSet<>(juegos2);
        set1.retainAll(set2);
        
        return new ArrayList<>(set1);
    }
    
    public List<String> obtenerPlataformasComunes(List<String> plataformas1, List<String> plataformas2) {
        if (plataformas1 == null || plataformas2 == null) return new ArrayList<>();
        
        Set<String> set1 = new HashSet<>(plataformas1);
        Set<String> set2 = new HashSet<>(plataformas2);
        set1.retainAll(set2);
        
        return new ArrayList<>(set1);
    }
    
    private double calcularCoeficienteJaccard(Set<String> set1, Set<String> set2) {
        if (set1.isEmpty() && set2.isEmpty()) return 0;
        
        Set<String> interseccion = new HashSet<>(set1);
        interseccion.retainAll(set2);
        
        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);
        
        return (double) interseccion.size() / union.size();
    }
    
    private double calcularCompatibilidadHorarios(String horarios1, String horarios2) {
        if (horarios1 == null || horarios2 == null || horarios1.isEmpty() || horarios2.isEmpty()) return 0;
        
        return 0.5;
    }
}