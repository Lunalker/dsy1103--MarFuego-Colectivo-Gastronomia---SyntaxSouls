package com.marfuego.ms_caja.repository;

import com.marfuego.ms_caja.model.FacturaCaja;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<FacturaCaja, Long> {
}
