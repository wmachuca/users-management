package com.amadeus.management.modules.usuarios.dto;

public record UsuarioCreateRequest(
        String correo,
        String nombres,
        String contrasena,
        String telefono,
        String apellidos
) {}
