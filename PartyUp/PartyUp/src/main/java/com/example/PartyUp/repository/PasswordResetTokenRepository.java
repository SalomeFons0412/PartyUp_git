package com.example.PartyUp.repository;

import com.example.PartyUp.model.entity.PasswordResetToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, String> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByToken(String token);
    void deleteByUserId(String userId);
}
