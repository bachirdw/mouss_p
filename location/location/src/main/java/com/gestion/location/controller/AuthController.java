package com.gestion.location.controller;

import com.gestion.location.model.AuthRequest;
import com.gestion.location.model.AuthResponse;
import com.gestion.location.model.User;
import com.gestion.location.repository.UserRepository;
import com.gestion.location.security.JwtUtil;
import com.gestion.location.security.LoginAttemptService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserRepository userRepository;
    @Autowired private LoginAttemptService attemptService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {
        String email = authRequest.getEmail();

        // 🔒 Vérification du blocage
        if (attemptService.isBlocked(email)) {
            return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .body("Compte temporairement bloqué après plusieurs tentatives échouées.");
        }

        try {
            // ✅ Authentification
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, authRequest.getPassword())
            );

            // ✅ Réinitialisation du compteur en cas de succès
            attemptService.loginSucceeded(email);

            // 🔄 Génération du token JWT
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            String token = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());

            return ResponseEntity.ok(new AuthResponse(token, user.getEmail(), user.getRole()));
        } catch (Exception ex) {
            // ❌ Incrément du compteur d'échecs
            attemptService.loginFailed(email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants invalides.");
        }
    }
}