package com.cafeteria.api.repository;

import com.cafeteria.api.entity.Pedido;
import com.cafeteria.api.entity.enums.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findAllByOrderByFechaDesc();
    List<Pedido> findByEstadoOrderByFechaDesc(EstadoPedido estado);
}
