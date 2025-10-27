package com.example.PartyUp.repository;

import com.example.PartyUp.model.entity.Grupo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends MongoRepository<Grupo, String> {
}
