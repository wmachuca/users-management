package com.amadeus.management.modules.usuarios.controller;

import com.amadeus.management.modules.usuarios.dto.UsuarioCreateRequest;
import com.amadeus.management.modules.usuarios.dto.UsuarioResponse;
import com.amadeus.management.modules.usuarios.dto.UsuarioUpdateRequest;
import com.amadeus.management.modules.usuarios.service.UsuariosService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Obtiene el listado de usuarios")
    public ResponseEntity<List<UsuarioResponse>> listar() {
        return ResponseEntity.ok(usuariosService.listar());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza un usuario existente por su id")
    public ResponseEntity<?> actualizar(@PathVariable("id") UUID id, @RequestBody UsuarioUpdateRequest request) {
        try {
            UsuarioResponse response = usuariosService.actualizar(id, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
