package com.Gastronomia.MarFuego.repository;

import com.Gastronomia.MarFuego.model.PlatoMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepositoryMenu extends JpaRepository<PlatoMenu, Long> {
    List<PlatoMenu> findByDisponibleTrue();
}
