package com.cafeteria.api.repository;

import com.cafeteria.api.entity.Pedido;
import com.cafeteria.api.entity.enums.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findAllByOrderByFechaDesc();
    List<Pedido> findByEstadoOrderByFechaDesc(EstadoPedido estado);
}
