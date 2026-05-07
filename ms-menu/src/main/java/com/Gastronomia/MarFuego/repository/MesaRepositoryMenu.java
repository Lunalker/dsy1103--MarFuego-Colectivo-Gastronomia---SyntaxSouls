
package com.Gastronomia.MarFuego.repository;

import com.Gastronomia.MarFuego.model.MesaMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepositoryMenu extends JpaRepository<MesaMenu, Long> {
}



