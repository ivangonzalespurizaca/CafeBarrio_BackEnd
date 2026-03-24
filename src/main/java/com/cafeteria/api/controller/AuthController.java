package com.cafeteria.api.controller;

import com.cafeteria.api.dto.LoginRequestDTO;
import com.cafeteria.api.dto.UsuarioDTO;
import com.cafeteria.api.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    // El estándar para cifrar contraseñas. Nunca se comparan textos planos.
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginDTO) {
        return usuarioRepository.findByEmail(loginDTO.getEmail())
                .map(u -> {
                    // BCrypt verifica si la clave enviada coincide con el hash de la BD
                    if (passwordEncoder.matches(loginDTO.getPassword(), u.getContrasenia())) {
                        UsuarioDTO response = new UsuarioDTO(
                                u.getEmail(),
                                u.getNombre(),
                                u.getRol().getNombre()
                        );
                        return ResponseEntity.ok(response);
                    }
                    return ResponseEntity.status(401).body("Contraseña incorrecta");
                })
                .orElse(ResponseEntity.status(404).body("Usuario no encontrado"));
    }
}