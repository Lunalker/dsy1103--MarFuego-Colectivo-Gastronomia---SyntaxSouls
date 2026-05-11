package com.marfuego.ms_pedidos.repository;

import com.marfuego.ms_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}