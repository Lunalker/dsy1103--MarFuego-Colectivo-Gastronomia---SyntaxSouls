
package com.Gastronomia.MarFuego.repository;

import com.Gastronomia.MarFuego.model.MesaGastronomia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepositoryGastronomia extends JpaRepository<MesaGastronomia, Long> {
}



