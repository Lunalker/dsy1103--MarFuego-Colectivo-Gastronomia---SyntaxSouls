package com.marfuego.ms_menu.repository;

import com.marfuego.ms_menu.model.MesaMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepositoryMenu extends JpaRepository<MesaMenu, Long> {
}



