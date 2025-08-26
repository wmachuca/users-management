package com.amadeus.management.usuarios.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String correo,
        String telefono,
        String nombres,
        String apellidos,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion
) {}
