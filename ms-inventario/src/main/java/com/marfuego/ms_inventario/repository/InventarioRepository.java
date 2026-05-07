package com.marfuego.ms_inventario.repository;

import com.marfuego.ms_inventario.model.InventarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<InventarioModel,Long> {
}
