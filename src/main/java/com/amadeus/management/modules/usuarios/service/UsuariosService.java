package com.amadeus.management.modules.usuarios.service;

import com.amadeus.management.modules.usuarios.dto.UsuarioCreateRequest;
import com.amadeus.management.modules.usuarios.dto.UsuarioResponse;
import com.amadeus.management.modules.usuarios.dto.UsuarioUpdateRequest;
import com.amadeus.management.modules.usuarios.model.Usuario;
import com.amadeus.management.modules.usuarios.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
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

    @Transactional
    public UsuarioResponse actualizar(UUID id, UsuarioUpdateRequest request) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (request.correo() != null && !request.correo().isBlank()) {
            String nuevoCorreo = request.correo().trim();
            if (!nuevoCorreo.equalsIgnoreCase(usuario.getCorreo())) {
                if (repository.existsByCorreoAndIdNot(nuevoCorreo, id)) {
                    throw new IllegalStateException("El correo ya está en uso");
                }
                usuario.setCorreo(nuevoCorreo);
            }
        }
        if (request.nombres() != null && !request.nombres().isBlank()) {
            usuario.setNombres(request.nombres());
        }
        if (request.telefono() != null && !request.telefono().isBlank()) {
            usuario.setTelefono(request.telefono());
        }
        if (request.apellidos() != null && !request.apellidos().isBlank()) {
            usuario.setApellidos(request.apellidos());
        }
        if (request.contrasena() != null && !request.contrasena().isBlank()) {
            usuario.setContrasena(passwordEncoder.encode(request.contrasena()));
        }

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

    @Transactional
    public void borrar(UUID id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        repository.deleteById(id);
    }
}
