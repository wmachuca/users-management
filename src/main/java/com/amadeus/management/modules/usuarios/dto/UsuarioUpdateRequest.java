package com.amadeus.management.modules.usuarios.dto;

public record UsuarioUpdateRequest(
        String correo,
        String nombres,
        String contrasena,
        String telefono,
        String apellidos
) {}
