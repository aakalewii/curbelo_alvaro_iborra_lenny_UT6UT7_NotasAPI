package com.ut7.actev.controller;

import com.ut7.actev.model.Usuario;
import com.ut7.actev.repository.UsuarioRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
public class UsuarioV2Controller {

    private final UsuarioRepository usuarioRepository;

    public UsuarioV2Controller(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public static class SignInRequest {
        @Email
        @NotBlank
        public String email;
        @NotBlank
        public String password;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Usuario> signIn(@RequestBody SignInRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        usuario.setPasswordHash(request.password);
        usuario.hashPassword();
        usuarioRepository.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }
}