package com.cafeteria.api.service.impl;

import com.cafeteria.api.dto.ProductoDTO;
import com.cafeteria.api.dto.ProductoSaveDTO;
import com.cafeteria.api.entity.Categoria;
import com.cafeteria.api.entity.Producto;
import com.cafeteria.api.mapper.ProductoMapper;
import com.cafeteria.api.repository.CategoriaRepository;
import com.cafeteria.api.repository.ProductoRepository;
import com.cafeteria.api.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;

    @Override
    public List<ProductoDTO> listarTodo() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toDto)
                .toList();
    }

    @Override
    public List<ProductoDTO> listarCatalogo() {
        return productoRepository.findByActivoTrue().stream()
                .map(productoMapper::toDto)
                .toList();
    }

    @Override
    public List<ProductoDTO> buscarPorCategoria(Long idCat) {
        return productoRepository.findByCategoriaIdAndActivoTrue(idCat).stream()
                .map(productoMapper::toDto)
                .toList();
    }

    @Override
    public ProductoDTO obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .map(productoMapper::toDto)
                .orElse(null);
    }

    @Override
    public ProductoDTO guardar(ProductoSaveDTO saveDto) {
        Producto producto = productoMapper.toEntity(saveDto);

        Categoria categoria = categoriaRepository.findById(saveDto.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + saveDto.getIdCategoria()));

        producto.setCategoria(categoria);

        if (saveDto.getId() != null && saveDto.getActivo() != null) {
            producto.setActivo(saveDto.getActivo());
        } else if (producto.getId() == null) {
            producto.setActivo(true);
        }
        Producto guardado = productoRepository.save(producto);
        return productoMapper.toDto(guardado);
    }

    @Override
    public void eliminarLogico(Long id) {
        productoRepository.findById(id).ifPresent(producto -> {
            producto.setActivo(false);
            productoRepository.save(producto);
        });
    }
}
