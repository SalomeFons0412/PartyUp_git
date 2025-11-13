package com.example.PartyUp.repository;

import com.example.PartyUp.model.entity.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    //Buscar usuario por correo (validacion)
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByNombre(String nombre);
    //Nuevo: obtener solo los usuarios que tienen activada la visibilidad de ubicación
    List<Usuario> findByMostrarUbicacionTrue();
}