package com.cafeteria.api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetallePedidoDTO {
    private String nombreProducto;
    private String imagenUrl;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
