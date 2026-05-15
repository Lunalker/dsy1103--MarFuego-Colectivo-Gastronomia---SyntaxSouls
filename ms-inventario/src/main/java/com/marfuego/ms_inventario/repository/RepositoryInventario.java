package com.marfuego.ms_inventario.repository;

import com.marfuego.ms_inventario.model.IngredienteInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryInventario extends JpaRepository<IngredienteInventario, Long> {
}