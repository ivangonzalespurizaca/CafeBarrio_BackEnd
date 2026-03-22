package com.cafeteria.api.service;

import com.cafeteria.api.dto.ProductoDTO;
import com.cafeteria.api.dto.ProductoSaveDTO;

import java.util.List;

public interface ProductoService {
    List<ProductoDTO> listarTodo();
    List<ProductoDTO> listarCatalogo();
    List<ProductoDTO> buscarPorCategoria(Long idCat);
    ProductoDTO obtenerPorId(Long id);
    ProductoDTO guardar(ProductoSaveDTO saveDTO);
    void eliminarLogico(Long id);
}
