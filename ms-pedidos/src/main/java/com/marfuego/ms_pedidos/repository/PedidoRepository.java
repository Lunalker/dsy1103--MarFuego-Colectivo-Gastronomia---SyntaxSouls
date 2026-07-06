package com.marfuego.ms_pedidos.repository;

import com.marfuego.ms_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de los pedidos. Además de lo típico de JpaRepository tiene
 * consultas para buscar pedidos por local y por estado.
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    /**
     * Trae los pedidos de un local.
     *
     * @param localId el id del local
     * @return los pedidos de ese local
     */
    List<Pedido> findByLocalId(Long localId);

    /**
     * Trae los pedidos que están en un estado dado.
     *
     * @param estado el estado por el que se filtra
     * @return los pedidos en ese estado
     */
    List<Pedido> findByEstado(String estado);
}
