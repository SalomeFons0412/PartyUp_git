package com.example.PartyUp.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.Instant;

@Data
@Document(collection = "password_reset_tokens")
public class PasswordResetToken {

    @Id
    private String id; // opcional, Mongo genera uno

    private String token;
    private String userId; // id del Usuario
    private Instant expiresAt;
}
