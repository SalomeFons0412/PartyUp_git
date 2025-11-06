package com.example.PartyUp.model.dto;

import java.util.List;
import java.util.ArrayList;

public class SugerenciaDTO {
    private String usuarioId;
    private String nombre;
    private String correo;  // ✅ CAMBIADO: email -> correo
    private Double porcentajeCompatibilidad;
    private List<String> juegosComunes;
    private List<String> plataformasComunes;
    private List<String> horariosCompatibles;
    
    // Constructores
    public SugerenciaDTO() {
        this.juegosComunes = new ArrayList<>();
        this.plataformasComunes = new ArrayList<>();
        this.horariosCompatibles = new ArrayList<>();
    }
    
    public SugerenciaDTO(String usuarioId, String nombre, Double porcentajeCompatibilidad) {
        this();
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.porcentajeCompatibilidad = porcentajeCompatibilidad;
    }
    
    // Getters y Setters
    public String getUsuarioId() { 
        return usuarioId; 
    }
    
    public void setUsuarioId(String usuarioId) { 
        this.usuarioId = usuarioId; 
    }
    
    public String getNombre() { 
        return nombre; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    
    public String getCorreo() { 
        return correo; 
    }
    
    public void setCorreo(String correo) { 
        this.correo = correo; 
    }
    
    public Double getPorcentajeCompatibilidad() { 
        return porcentajeCompatibilidad; 
    }
    
    public void setPorcentajeCompatibilidad(Double porcentajeCompatibilidad) { 
        this.porcentajeCompatibilidad = porcentajeCompatibilidad; 
    }
    
    public List<String> getJuegosComunes() { 
        return juegosComunes; 
    }
    
    public void setJuegosComunes(List<String> juegosComunes) { 
        this.juegosComunes = juegosComunes; 
    }
    
    public List<String> getPlataformasComunes() { 
        return plataformasComunes; 
    }
    
    public void setPlataformasComunes(List<String> plataformasComunes) { 
        this.plataformasComunes = plataformasComunes; 
    }
    
    public List<String> getHorariosCompatibles() { 
        return horariosCompatibles; 
    }
    
    public void setHorariosCompatibles(List<String> horariosCompatibles) { 
        this.horariosCompatibles = horariosCompatibles; 
    }
}