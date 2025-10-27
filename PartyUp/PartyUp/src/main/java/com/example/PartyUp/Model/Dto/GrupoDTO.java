package com.example.partyup.model.dto;

import java.util.List;

public class GrupoDTO {
    private String id;
    private String nombre;
    private String descripcion;
    private String creadorId;
    private List<String> miembrosIds;

    // Constructor vacío
    public GrupoDTO() {}

    // Constructor completo
    public GrupoDTO(String id, String nombre, String descripcion, String creadorId, List<String> miembrosIds) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creadorId = creadorId;
        this.miembrosIds = miembrosIds;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCreadorId() { return creadorId; }
    public void setCreadorId(String creadorId) { this.creadorId = creadorId; }

    public List<String> getMiembrosIds() { return miembrosIds; }
    public void setMiembrosIds(List<String> miembrosIds) { this.miembrosIds = miembrosIds; }
}
