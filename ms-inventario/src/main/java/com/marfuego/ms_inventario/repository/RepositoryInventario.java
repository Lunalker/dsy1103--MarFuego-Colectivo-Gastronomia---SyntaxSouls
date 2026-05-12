package com.marfuego.ms_inventario.repository;

import com.marfuego.ms_inventario.model.IngredienteInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryInventario extends JpaRepository<IngredienteInventario, Long> {

    // R2: trae los ingredientes cuyo stockActual ya bajo del minimo
    @Query("SELECT i FROM IngredienteInventario i WHERE i.stockActual < i.stockMinimo")
    List<IngredienteInventario> findEnAlerta();
}
