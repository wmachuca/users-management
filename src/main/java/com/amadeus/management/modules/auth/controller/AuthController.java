package com.amadeus.management.modules.auth.controller;

import com.amadeus.management.modules.auth.dto.LoginRequest;
import com.amadeus.management.modules.auth.dto.LoginResponse;
import com.amadeus.management.modules.auth.service.JwtService;
import com.amadeus.management.modules.usuarios.model.Usuario;
import com.amadeus.management.modules.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtService jwtService;

    public AuthController(UsuarioRepository usuarioRepository,
                          @Value("${security.jwt.secret}") String jwtSecret,
                          @Value("${security.jwt.expiration-seconds:3600}") long expirationSeconds) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = new JwtService(jwtSecret, expirationSeconds);
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autentica al usuario y retorna un token JWT", security = {})
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return usuarioRepository.findByCorreo(request.correo())
                .filter(u -> passwordEncoder.matches(request.contrasena(), u.getContrasena()))
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(toLoginResponse(u)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Credenciales inválidas")));
    }

    private LoginResponse toLoginResponse(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", usuario.getId().toString());
        claims.put("correo", usuario.getCorreo());
        String token = jwtService.generateToken(usuario.getCorreo(), claims);
        return new LoginResponse(token, "Bearer", jwtService.getExpirationSeconds());
    }
}
