package com.Gastronomia.MarFuego.repository;

import com.Gastronomia.MarFuego.model.PlatoMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio del menú. Extiende de JpaRepository (guardar, buscar, borrar) y
 * agrega una consulta propia para la regla R1.
 */
@Repository
public interface RepositoryMenu extends JpaRepository<PlatoMenu, Long> {

    /**
     * Trae los platos que están disponibles en un local (regla R1). Spring arma
     * la consulta sola a partir del nombre del método.
     *
     * @param localId el id del local
     * @return los platos disponibles de ese local
     */
    List<PlatoMenu> findByLocalIdAndDisponibleTrue(Long localId);
}
