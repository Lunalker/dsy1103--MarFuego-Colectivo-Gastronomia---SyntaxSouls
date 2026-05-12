package com.Gastronomia.MarFuego.repository;

import com.Gastronomia.MarFuego.model.PlatoMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryMenu extends JpaRepository<PlatoMenu, Long> {

    // Trae los platos disponibles de un local (R1)
    List<PlatoMenu> findByLocalIdAndDisponibleTrue(Long localId);
}
