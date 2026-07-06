package com.marfuego.ms_inventario.repository;

import com.marfuego.ms_inventario.model.IngredienteInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio del inventario. Extiende de JpaRepository, así que ya trae los
 * métodos típicos (guardar, buscar, borrar) y además tiene una consulta propia
 * para la regla R2.
 */
@Repository
public interface RepositoryInventario extends JpaRepository<IngredienteInventario, Long> {

    /**
     * Trae los ingredientes cuyo stock actual ya quedó por debajo del mínimo,
     * que son los que están en alerta y hay que reponer (regla R2).
     *
     * @return la lista de ingredientes bajo el mínimo
     */
    @Query("SELECT i FROM IngredienteInventario i WHERE i.stockActual < i.stockMinimo")
    List<IngredienteInventario> findEnAlerta();
}
