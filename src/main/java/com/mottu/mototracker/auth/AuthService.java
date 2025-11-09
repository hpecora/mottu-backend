package com.mottu.mototracker.auth;

import com.mottu.mototracker.auth.dto.*;
import com.mottu.mototracker.model.User;
import com.mottu.mototracker.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public AuthService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public AuthResponse signup(SignupRequest req) {
        repo.findByEmail(req.email()).ifPresent(u -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail já cadastrado");
        });

        User u = repo.save(User.builder()
                .name(req.name())
                .email(req.email())
                .passwordHash(encoder.encode(req.password()))
                .build());

        // token fake só pra fluxo do mobile; depois trocamos por JWT
        String token = UUID.randomUUID().toString();
        return new AuthResponse(u.getId(), u.getName(), u.getEmail(), token);
    }

    public AuthResponse login(LoginRequest req) {
        User u = repo.findByEmail(req.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas"));

        if (!encoder.matches(req.password(), u.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
        }

        String token = UUID.randomUUID().toString();
        return new AuthResponse(u.getId(), u.getName(), u.getEmail(), token);
    }
}
