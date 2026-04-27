package com.marfuego.ms_menu.repository;

import com.marfuego.ms_menu.model.PlatoMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepositoryMenu extends JpaRepository<PlatoMenu, Long> {
    List<PlatoMenu> findByDisponibleTrue();
}