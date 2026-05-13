package com.marfuego.ms_caja.repository;

import com.marfuego.ms_caja.model.BoletaCaja;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoletaRepository extends JpaRepository<BoletaCaja, Long> {
}
