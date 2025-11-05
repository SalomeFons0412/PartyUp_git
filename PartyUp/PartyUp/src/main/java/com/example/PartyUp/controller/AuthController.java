package com.example.PartyUp.controller;

import com.example.PartyUp.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PasswordResetService passwordResetService;

    /**
     * POST /auth/forgot-password
     * Body: { "correo": "usuario@ejemplo.com" }
     * No devolveremos error si el correo no existe (por seguridad).
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        String correo = body.get("correo");
        if (correo == null || correo.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "correo es requerido"));
        }
        passwordResetService.createAndSendResetToken(correo);
        return ResponseEntity.ok(Map.of("message", "Si el correo existe, se ha enviado un token temporal."));
    }

    /**
     * POST /auth/reset-password
     * Body: { "token": "...", "newPassword": "nuevaContraseña123" }
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String newPassword = body.get("newPassword");
        if (token == null || token.isBlank() || newPassword == null || newPassword.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "token y newPassword son requeridos"));
        }

        boolean ok = passwordResetService.resetPassword(token, newPassword);
        if (!ok) {
            return ResponseEntity.status(400).body(Map.of("message", "Token inválido o expirado"));
        }
        return ResponseEntity.ok(Map.of("message", "Contraseña actualizada correctamente"));
    }
}
