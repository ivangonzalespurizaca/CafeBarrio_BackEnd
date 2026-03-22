package com.cafeteria.api.service.impl;

import com.cafeteria.api.dto.DetalleRequestDTO;
import com.cafeteria.api.dto.PedidoRequestDTO;
import com.cafeteria.api.dto.PedidoResponseDTO;
import com.cafeteria.api.entity.DetallePedido;
import com.cafeteria.api.entity.Pedido;
import com.cafeteria.api.entity.Producto;
import com.cafeteria.api.entity.enums.EstadoPedido;
import com.cafeteria.api.mapper.PedidoMapper;
import com.cafeteria.api.repository.PedidoRepository;
import com.cafeteria.api.repository.ProductoRepository;
import com.cafeteria.api.service.PedidoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final PedidoMapper pedidoMapper;

    @Override
    @Transactional
    public PedidoResponseDTO registrarPedido(PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setFecha(LocalDateTime.now());
        pedido.setClienteNombre(dto.getClienteNombre());
        pedido.setCelular(dto.getCelular());
        pedido.setDireccion(dto.getDireccion());
        pedido.setEstado(EstadoPedido.PENDIENTE);

        List<DetallePedido> detalles = new ArrayList<>();
        BigDecimal totalAcumulado = BigDecimal.ZERO;

        for (DetalleRequestDTO itemDto : dto.getItems()){
            Producto producto = productoRepository.findById(itemDto.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + itemDto.getIdProducto()));

            if(producto.getStock() < itemDto.getCantidad()){
                throw new RuntimeException("Stock insuficiente para producto: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - itemDto.getCantidad());
            productoRepository.save(producto);

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(itemDto.getCantidad());

            detalle.setPrecioUnitario(producto.getPrecio());

            BigDecimal subtotal = producto.getPrecio().multiply(new BigDecimal(itemDto.getCantidad()));
            detalle.setSubtotal(subtotal);

            totalAcumulado = totalAcumulado.add(subtotal);
            detalles.add(detalle);
        }

        pedido.setDetalles(detalles);
        pedido.setTotal(totalAcumulado);

        Pedido guardado = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(guardado);
    }

    @Override
    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAllByOrderByFechaDesc().stream()
                .map(pedidoMapper::toDto)
                .toList();
    }

    @Override
    public List<PedidoResponseDTO> listarPorEstado(EstadoPedido estado) {
        return pedidoRepository.findByEstadoOrderByFechaDesc(estado).stream()
                .map(pedidoMapper::toDto)
                .toList();
    }

    @Override
    public PedidoResponseDTO cambiarEstado(Long id, EstadoPedido nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + id));

        pedido.setEstado(nuevoEstado);
        return pedidoMapper.toDto(pedidoRepository.save(pedido));
    }
}
