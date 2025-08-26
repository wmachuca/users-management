package com.amadeus.management.modules.usuarios.service;

import com.amadeus.management.modules.usuarios.dto.UsuarioCreateRequest;
import com.amadeus.management.modules.usuarios.dto.UsuarioResponse;
import com.amadeus.management.modules.usuarios.model.Usuario;
import com.amadeus.management.modules.usuarios.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {
        return repository.findAll().stream()
                .map(u -> new UsuarioResponse(
                        u.getId(),
                        u.getCorreo(),
                        u.getTelefono(),
                        u.getNombres(),
                        u.getApellidos(),
                        u.getFechaCreacion(),
                        u.getFechaActualizacion()
                ))
                .collect(Collectors.toList());
    }
}
