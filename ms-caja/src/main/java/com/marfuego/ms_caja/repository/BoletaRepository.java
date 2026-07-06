package com.marfuego.ms_caja.repository;

import com.marfuego.ms_caja.model.BoletaCaja;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de las boletas. Además de lo típico de JpaRepository tiene la
 * consulta que usa la regla R4 para encontrar la boleta de un pedido.
 */
public interface BoletaRepository extends JpaRepository<BoletaCaja, Long> {

    /**
     * Trae la boleta que corresponde a un pedido (regla R4).
     *
     * @param pedidoId el id del pedido
     * @return la boleta de ese pedido, o null si no existe
     */
    BoletaCaja findByPedidoId(Long pedidoId);
}
