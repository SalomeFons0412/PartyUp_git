package com.example.PartyUp.model.dto;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Data
public class UsuarioDTO {
    private String id;
    private String nombre;
    private String correo;
    private String telefono;
    
    //compatibilidad
    private List<String> juegos = new ArrayList<>();
    private List<String> plataformas = new ArrayList<>();
    private String horarios;
}