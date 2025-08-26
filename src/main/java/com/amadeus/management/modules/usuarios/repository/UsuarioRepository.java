package com.amadeus.management.modules.usuarios.repository;

import com.amadeus.management.modules.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    boolean existsByCorreo(String correo);
    Optional<Usuario> findByCorreo(String correo);
}