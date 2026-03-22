package com.cafeteria.api.controller;

import com.cafeteria.api.dto.ProductoDTO;
import com.cafeteria.api.dto.ProductoSaveDTO;
import com.cafeteria.api.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {
    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarCatalogo() {
        return ResponseEntity.ok(productoService.listarCatalogo());
    }

    @GetMapping("/admin")
    public ResponseEntity<List<ProductoDTO>> listarTodo() {
        return ResponseEntity.ok(productoService.listarTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @GetMapping("/categoria/{idCat}")
    public ResponseEntity<List<ProductoDTO>> filtrarPorCategoria(@PathVariable Long idCat) {
        return ResponseEntity.ok(productoService.buscarPorCategoria(idCat));
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> guardar(@Valid @RequestBody ProductoSaveDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoSaveDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(productoService.guardar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }
}
