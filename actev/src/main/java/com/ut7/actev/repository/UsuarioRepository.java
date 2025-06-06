package com.ut7.actev.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ut7.actev.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}