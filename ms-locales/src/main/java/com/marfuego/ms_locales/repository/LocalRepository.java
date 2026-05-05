package com.marfuego.ms_locales.repository;

import com.marfuego.ms_locales.model.Local;
import com.marfuego.ms_locales.model.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {

    // Buscar local por su ubicacion
    List<Local> findByUbicacion(Ubicacion ubicacion);

}
