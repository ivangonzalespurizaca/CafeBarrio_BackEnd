package com.cafeteria.api.service;

import com.cafeteria.api.dto.ProductoDTO;
import com.cafeteria.api.dto.ProductoSaveDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductoService {
    List<ProductoDTO> listarTodo();
    List<ProductoDTO> listarCatalogo();
    List<ProductoDTO> buscarPorCategoria(Long idCat);
    ProductoDTO obtenerPorId(Long id);
    ProductoDTO guardarConImagen(ProductoSaveDTO saveDTO, MultipartFile archivo);
    void eliminarLogico(Long id);
}
