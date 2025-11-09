package com.example.PartyUp.model.dto;

public class BusquedaFiltrosDTO {
    private String ciudad;
    private String plataforma;
    private String juego;
    private Double distanciaMaxima; // en km
    private Double latitudUsuario;
    private Double longitudUsuario;
    
    // Constructores
    public BusquedaFiltrosDTO() {}
    
    // Getters y Setters
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    
    public String getPlataforma() { return plataforma; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }
    
    public String getJuego() { return juego; }
    public void setJuego(String juego) { this.juego = juego; }
    
    public Double getDistanciaMaxima() { return distanciaMaxima; }
    public void setDistanciaMaxima(Double distanciaMaxima) { this.distanciaMaxima = distanciaMaxima; }
    
    public Double getLatitudUsuario() { return latitudUsuario; }
    public void setLatitudUsuario(Double latitudUsuario) { this.latitudUsuario = latitudUsuario; }
    
    public Double getLongitudUsuario() { return longitudUsuario; }
    public void setLongitudUsuario(Double longitudUsuario) { this.longitudUsuario = longitudUsuario; }
}