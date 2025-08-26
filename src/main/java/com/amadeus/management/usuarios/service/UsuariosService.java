package com.amadeus.management.usuarios.service;

import com.amadeus.management.usuarios.dto.UsuarioCreateRequest;
import com.amadeus.management.usuarios.dto.UsuarioResponse;
import com.amadeus.management.usuarios.model.Usuario;
import com.amadeus.management.usuarios.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuariosService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuariosService(UsuarioRepository repository) {
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public UsuarioResponse crear(UsuarioCreateRequest request) {
        Usuario usuario = new Usuario();
        usuario.setCorreo(request.correo());
        usuario.setNombres(request.nombres());
        if (request.telefono() != null && !request.telefono().isBlank()) {
            usuario.setTelefono(request.telefono());
        }
        if (request.apellidos() != null && !request.apellidos().isBlank()) {
            usuario.setApellidos(request.apellidos());
        }
        usuario.setContrasena(passwordEncoder.encode(request.contrasena()));
        Usuario saved = repository.save(usuario);
        return new UsuarioResponse(
                saved.getId(),
                saved.getCorreo(),
                saved.getTelefono(),
                saved.getNombres(),
                saved.getApellidos(),
                saved.getFechaCreacion(),
                saved.getFechaActualizacion()
        );
    }
}
