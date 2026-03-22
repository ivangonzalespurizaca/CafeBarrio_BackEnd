package com.cafeteria.api.mapper;

import com.cafeteria.api.dto.CategoriaDTO;
import com.cafeteria.api.entity.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriaMapper {
    CategoriaDTO toDto(Categoria categoria);
}
