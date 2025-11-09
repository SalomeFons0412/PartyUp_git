package com.example.PartyUp.repository;

import com.example.PartyUp.model.entity.Mensaje;
import com.example.PartyUp.model.entity.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeRepository extends MongoRepository<Mensaje, String> {
    
    @Query("{ $or: [ " +
           "{ 'remitente.$id': ?0, 'destinatario.$id': ?1 }, " +
           "{ 'remitente.$id': ?1, 'destinatario.$id': ?0 } " +
           "] }")
    List<Mensaje> findConversacion(String usuario1Id, String usuario2Id);
    
    List<Mensaje> findByDestinatarioAndLeidoFalse(Usuario destinatario);
    
    List<Mensaje> findByRemitenteOrDestinatario(Usuario remitente, Usuario destinatario);
    
    void deleteByRemitenteAndDestinatarioOrRemitenteAndDestinatario(
        Usuario usuario1, Usuario usuario2, Usuario usuario3, Usuario usuario4);
}