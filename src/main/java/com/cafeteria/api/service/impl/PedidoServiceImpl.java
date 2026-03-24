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
    @Transactional // Si algo falla, se hace Rollback de todo (no se descuenta stock si no se guarda el pedido).
    public PedidoResponseDTO registrarPedido(PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setFecha(LocalDateTime.now());
        pedido.setClienteNombre(dto.getClienteNombre());
        pedido.setCelular(dto.getCelular());
        pedido.setDireccion(dto.getDireccion());
        pedido.setEstado(EstadoPedido.PENDIENTE); // Estado inicial por defecto

        List<DetallePedido> detalles = new ArrayList<>();
        BigDecimal totalAcumulado = BigDecimal.ZERO; // Inicializamos el acumulador de dinero

        for (DetalleRequestDTO itemDto : dto.getItems()){
            // Validación de existencia del producto
            Producto producto = productoRepository.findById(itemDto.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + itemDto.getIdProducto()));

            // Regla de negocio vital para evitar ventas sin producto físico
            if(producto.getStock() < itemDto.getCantidad()){
                throw new RuntimeException("Stock insuficiente para producto: " + producto.getNombre());
            }

            // Descontamos el stock del producto
            producto.setStock(producto.getStock() - itemDto.getCantidad());
            productoRepository.save(producto);

            // Creamos el detalle del pedido
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(itemDto.getCantidad());

            detalle.setPrecioUnitario(producto.getPrecio());

            // Calculamos el subtotal del detalle (precio unitario * cantidad)
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
