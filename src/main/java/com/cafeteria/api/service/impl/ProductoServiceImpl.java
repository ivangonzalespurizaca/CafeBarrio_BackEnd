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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

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
    public ProductoDTO guardarConImagen(ProductoSaveDTO dto, MultipartFile archivo) {
        Producto producto;

        // Validación de existencia de la categoría antes de proceder
        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + dto.getIdCategoria()));

        // Lógica dual: Actualizar si existe ID, o Crear si es null
        if (dto.getId() != null) {
            producto = productoRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            // Actualización manual de campos para evitar sobreescritura accidental
            producto.setNombre(dto.getNombre());
            producto.setPrecio(dto.getPrecio());
            producto.setStock(dto.getStock());
            producto.setDescripcion(dto.getDescripcion());
            producto.setCategoria(categoria);
        } else {
            producto = productoMapper.toEntity(dto);
            producto.setCategoria(categoria);
            producto.setActivo(true);
        }

        // Solo se procesa si se proporciona un nuevo archivo, de lo contrario se mantiene la URL existente
        if (archivo != null && !archivo.isEmpty()) {
            try {
                // Definición y creación del directorio de subidas si no existe
                Path directorioImagenes = Paths.get("uploads");
                if (!Files.exists(directorioImagenes)) Files.createDirectories(directorioImagenes);

                // Generación de nombre único con UUID para evitar conflictos de archivos iguales
                String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();
                Path rutaCompleta = directorioImagenes.resolve(nombreArchivo);

                // Copia física del archivo al servidor
                Files.copy(archivo.getInputStream(), rutaCompleta, StandardCopyOption.REPLACE_EXISTING);

                // Construcción de la URL de acceso estático
                producto.setImagenUrl("http://localhost:8080/uploads/" + nombreArchivo);
            } catch (IOException e) {
                throw new RuntimeException("Error al procesar la imagen", e);
            }
        } else if (producto.getImagenUrl() == null) {
            producto.setImagenUrl("https://res.cloudinary.com/dfid8iuf3/image/upload/v1774239314/Gemini_Generated_Image_7jc647jc647jc647_jzi5f8.png");
        }

        return productoMapper.toDto(productoRepository.save(producto));
    }

    @Override
    public void eliminarLogico(Long id) {
        // Toggle de estado: cambia de activo a inactivo o viceversa sin borrar de la BD
        productoRepository.findById(id).ifPresent(producto -> {
            producto.setActivo(!producto.getActivo());
            productoRepository.save(producto);
        });
    }
}
