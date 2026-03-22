package com.cafeteria.api.mapper;

import com.cafeteria.api.dto.ProductoDTO;
import com.cafeteria.api.dto.ProductoSaveDTO;
import com.cafeteria.api.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductoMapper {
    @Mapping(source = "categoria.id", target = "idCategoria")
    @Mapping(source = "categoria.nombre", target = "nombreCategoria")
    ProductoDTO toDto(Producto producto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "idCategoria", target = "categoria.id")
    @Mapping(target = "categoria.nombre", ignore = true)
    @Mapping(target = "activo", ignore = true)
    Producto toEntity(ProductoSaveDTO saveDto);
}
