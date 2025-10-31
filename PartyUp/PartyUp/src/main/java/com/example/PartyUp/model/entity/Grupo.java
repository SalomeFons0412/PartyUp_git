package com.example.PartyUp.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "grupos")
public class Grupo {

    @Id
    private String id;

    private String nombre;
    private String descripcion;

    // Referencia al creador (guardaremos el ID del usuario)
    private String creadorId;

    // Lista de IDs de usuarios miembros
    private List<String> miembros = new ArrayList<>();

    // Constructores
    public Grupo() {}

    public Grupo(String nombre, String descripcion, String creadorId) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creadorId = creadorId;
        this.miembros = new ArrayList<>();
        this.miembros.add(creadorId); // el creador también es miembro
    }

    // Métodos
    public void agregarMiembro(String usuarioId) {
        if (!miembros.contains(usuarioId)) {
            miembros.add(usuarioId);
        }
    }

    public void expulsarMiembro(String usuarioId) {
        miembros.remove(usuarioId);
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCreadorId() {
        return creadorId;
    }

    public void setCreadorId(String creadorId) {
        this.creadorId = creadorId;
    }

    public List<String> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<String> miembros) {
        this.miembros = miembros;
    }
}
