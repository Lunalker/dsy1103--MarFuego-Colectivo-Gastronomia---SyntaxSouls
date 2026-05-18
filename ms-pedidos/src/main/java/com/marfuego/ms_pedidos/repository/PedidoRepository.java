package com.marfuego.ms_pedidos.repository;

import com.marfuego.ms_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByLocalId(Long localId);

    List<Pedido> findByEstado(String estado);
}
