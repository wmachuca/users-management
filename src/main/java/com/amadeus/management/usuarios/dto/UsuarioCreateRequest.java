package com.amadeus.management.usuarios.dto;

public record UsuarioCreateRequest(
        String correo,
        String nombres,
        String contrasena,
        String telefono,
        String apellidos
) {}
