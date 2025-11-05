package com.example.PartyUp.service;

import com.example.PartyUp.model.entity.PasswordResetToken;
import com.example.PartyUp.model.entity.Usuario;
import com.example.PartyUp.repository.PasswordResetTokenRepository;
import com.example.PartyUp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    private static final long TOKEN_MINUTES = 15; // token válido 15 minutos

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void createAndSendResetToken(String correo) {
        Optional<Usuario> userOpt = usuarioRepository.findByCorreo(correo);
        if (userOpt.isEmpty()) {
            // Para no filtrar usuarios, es buena práctica no revelar si no existe.
            return;
        }

        Usuario user = userOpt.get();
        // Eliminar tokens antiguos del usuario
        tokenRepository.deleteByUserId(user.getId());

        String token = UUID.randomUUID().toString();
        PasswordResetToken prt = new PasswordResetToken();
        prt.setToken(token);
        prt.setUserId(user.getId());
        prt.setExpiresAt(Instant.now().plus(TOKEN_MINUTES, ChronoUnit.MINUTES));

        tokenRepository.save(prt);

        // Construir enlace — cambia la URL por la de tu frontend
        String resetLink = String.format("https://tusitio.com/reset-password?token=%s", token);

        String subject = "Recuperación de contraseña - Token temporal";
        String text = "Hola " + user.getNombre() + ",\n\n"
                + "Solicitaste recuperar tu contraseña. Usa el siguiente enlace para restablecerla. "
                + "El enlace expirará en " + TOKEN_MINUTES + " minutos.\n\n"
                + resetLink + "\n\nSi no solicitaste esto, ignora este correo.";

        mailService.sendEmail(user.getCorreo(), subject, text);
    }

    public Optional<Usuario> validatePasswordResetToken(String token) {
        Optional<PasswordResetToken> prtOpt = tokenRepository.findByToken(token);
        if (prtOpt.isEmpty()) return Optional.empty();

        PasswordResetToken prt = prtOpt.get();
        if (prt.getExpiresAt().isBefore(Instant.now())) {
            // Token expirado -> eliminar
            tokenRepository.deleteByToken(token);
            return Optional.empty();
        }

        return usuarioRepository.findById(prt.getUserId());
    }

    /**
     * Resetea la contraseña si el token es válido. Retorna true si éxito.
     */
    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> prtOpt = tokenRepository.findByToken(token);
        if (prtOpt.isEmpty()) return false;

        PasswordResetToken prt = prtOpt.get();
        if (prt.getExpiresAt().isBefore(Instant.now())) {
            tokenRepository.deleteByToken(token);
            return false;
        }

        Optional<Usuario> userOpt = usuarioRepository.findById(prt.getUserId());
        if (userOpt.isEmpty()) return false;

        Usuario user = userOpt.get();

        // Cifrar nueva contraseña
        user.setContrasena(passwordEncoder.encode(newPassword));
        usuarioRepository.save(user);

        // Eliminar token para que no se reuse
        tokenRepository.deleteByToken(token);
        return true;
    }
}
