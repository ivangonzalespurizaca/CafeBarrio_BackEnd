package com.cafeteria.api.controller;

import com.cafeteria.api.dto.PedidoRequestDTO;
import com.cafeteria.api.dto.PedidoResponseDTO;
import com.cafeteria.api.entity.enums.EstadoPedido;
import com.cafeteria.api.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PedidoController {
    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody PedidoRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.registrarPedido(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorEstado(@PathVariable EstadoPedido estado) {
        return ResponseEntity.ok(pedidoService.listarPorEstado(estado));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        try {
            String estadoStr = body.get("estado");
            if (estadoStr == null) {
                return ResponseEntity.badRequest().body(Map.of("message", "El campo 'estado' es obligatorio"));
            }

            // Convertimos a Enum de forma segura
            EstadoPedido nuevoEstado = EstadoPedido.valueOf(estadoStr.toUpperCase());

            return ResponseEntity.ok(pedidoService.cambiarEstado(id, nuevoEstado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Estado no válido. Use: PENDIENTE, ENTREGADO, etc."));
        }
    }
}
