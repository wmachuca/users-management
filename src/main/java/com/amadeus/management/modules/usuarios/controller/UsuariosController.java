package com.amadeus.management.modules.usuarios.controller;

import com.amadeus.management.modules.usuarios.dto.UsuarioCreateRequest;
import com.amadeus.management.modules.usuarios.dto.UsuarioResponse;
import com.amadeus.management.modules.usuarios.service.UsuariosService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    private final UsuariosService usuariosService;

    public UsuariosController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @PostMapping
    @Operation(summary = "Crear usuario", description = "Crea un nuevo usuario", security = {})
    public ResponseEntity<UsuarioResponse> crear(@RequestBody UsuarioCreateRequest request) {
        UsuarioResponse response = usuariosService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
