package com.example.PartyUp.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "publicaciones")
public class Publicacion {

    @Id
    private String id;

    private String autorId;      // id del usuario que publica
    private String autorNombre;  // nombre visible del autor
    private String contenido;    // texto opcional
    private String tipo;         // "texto", "imagen", "video"
    private String urlMedia;     // si es imagen o video, URL del archivo

    private LocalDateTime fecha; // fecha de publicación
}
