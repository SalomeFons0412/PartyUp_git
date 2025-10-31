package com.example.PartyUp.repository;

import com.example.PartyUp.model.entity.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    //Buscar usuario por correo (validacion)
    Optional<Usuario> findByCorreo(String correo);
}