package com.cafeteria.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDTO {
    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String clienteNombre;

    @NotBlank(message = "El celular es obligatorio")
    @Size(min = 9, max = 15, message = "El celular debe tener entre 9 y 20 dígitos")
    private String celular;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotEmpty(message = "El pedido debe tener al menos un producto")
    @Valid
    private List<DetalleRequestDTO> items;
}
