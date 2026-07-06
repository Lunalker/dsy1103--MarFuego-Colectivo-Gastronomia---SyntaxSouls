package com.marfuego.ms_caja.repository;

import com.marfuego.ms_caja.model.FacturaCaja;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de las facturas. Trae los métodos típicos de JpaRepository para
 * guardar, buscar y borrar.
 */
public interface FacturaRepository extends JpaRepository<FacturaCaja, Long> {
}
