package com.Gastronomia.MarFuego.repository;

import com.Gastronomia.MarFuego.model.PlatoGastronomia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepositoryGastronomia extends JpaRepository<PlatoGastronomia, Long> {
    List<PlatoGastronomia> findByDisponibleTrue();
}
