package com.amadeus.management.usuarios.repository;

import com.amadeus.management.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    boolean existsByCorreo(String correo);
}