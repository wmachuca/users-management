package com.amadeus.management.usuarios.controller;

import com.amadeus.management.usuarios.dto.UsuarioCreateRequest;
import com.amadeus.management.usuarios.dto.UsuarioResponse;
import com.amadeus.management.usuarios.service.UsuariosService;
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
    public ResponseEntity<UsuarioResponse> crear(@RequestBody UsuarioCreateRequest request) {
        UsuarioResponse response = usuariosService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
