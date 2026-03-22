package com.cafeteria.api.service;

import com.cafeteria.api.dto.PedidoRequestDTO;
import com.cafeteria.api.dto.PedidoResponseDTO;
import com.cafeteria.api.entity.enums.EstadoPedido;

import java.util.List;

public interface PedidoService {
    PedidoResponseDTO registrarPedido(PedidoRequestDTO dto);
    List<PedidoResponseDTO> listarTodos();
    List<PedidoResponseDTO> listarPorEstado(EstadoPedido estado);
    PedidoResponseDTO cambiarEstado(Long id, EstadoPedido nuevoEstado);
}
