package com.cafeteria.api.mapper;

import com.cafeteria.api.dto.DetallePedidoDTO;
import com.cafeteria.api.dto.PedidoResponseDTO;
import com.cafeteria.api.entity.DetallePedido;
import com.cafeteria.api.entity.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PedidoMapper {
    PedidoResponseDTO toDto(Pedido pedido);

    @Mapping(source = "producto.nombre", target = "nombreProducto")
    @Mapping(source = "producto.imagenUrl", target = "imagenUrl")
    DetallePedidoDTO toDetalleDto(DetallePedido detalle);
}
