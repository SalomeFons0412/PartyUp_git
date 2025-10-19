package com.example.PartyUp.Model.Entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grupo")
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nombre;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "creador_id")
    private Usuario creador;

    @ManyToMany
    @JoinTable(
        name = "grupo_miembros",
        joinColumns = @JoinColumn(name = "grupo_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> miembros = new ArrayList<>();

    //Constructores
    public Grupo() {}

    public Grupo(String nombre, String descripcion, Usuario creador) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creador = creador;
        this.miembros = new ArrayList<>();
        this.miembros.add(creador);
    }

    //Metodos
    public void agregarMiembro(Usuario usuario) {
        if (!miembros.contains(usuario)) {
            miembros.add(usuario);
        }
    }

    public void expulsarMiembro(Usuario usuario) {
        miembros.remove(usuario);
    }

    //Getters y Setters
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

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public List<Usuario> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<Usuario> miembros) {
        this.miembros = miembros;
    }

}
