package com.example.PartyUp.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "mensajes")
public class Mensaje {
    
    @Id
    private String id; // MongoDB usa String para ID

    private String contenido;
    private LocalDateTime fechaEnvio;
    private boolean leido = false;

    @DBRef
    private Usuario remitente;

    @DBRef
    private Usuario destinatario;

    // Constructores
    public Mensaje() {}

    public Mensaje(String contenido, Usuario remitente, Usuario destinatario) {
        this.contenido = contenido;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.fechaEnvio = LocalDateTime.now();
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public Usuario getRemitente() { return remitente; }
    public void setRemitente(Usuario remitente) { this.remitente = remitente; }

    public Usuario getDestinatario() { return destinatario; }
    public void setDestinatario(Usuario destinatario) { this.destinatario = destinatario; }

    public boolean isLeido() { return leido; }
    public void setLeido(boolean leido) { this.leido = leido; }
}