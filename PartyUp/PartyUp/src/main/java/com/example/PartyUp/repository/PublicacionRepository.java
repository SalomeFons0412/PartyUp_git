package com.example.PartyUp.repository;

import com.example.PartyUp.model.entity.Publicacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PublicacionRepository extends MongoRepository<Publicacion, String> {
    List<Publicacion> findAllByOrderByFechaDesc(); // devuelve publicaciones recientes
}
