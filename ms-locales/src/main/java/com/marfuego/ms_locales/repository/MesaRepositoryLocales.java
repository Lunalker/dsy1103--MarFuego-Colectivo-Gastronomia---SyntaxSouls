package com.marfuego.ms_locales.repository;

import com.marfuego.ms_locales.model.Estado;
import com.marfuego.ms_locales.model.MesaLocales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de las mesas. Además de lo típico de JpaRepository tiene consultas
 * para buscar mesas por local y por estado, que se usan en la regla R3.
 */
@Repository
public interface MesaRepositoryLocales extends JpaRepository<MesaLocales, Long> {

    /**
     * Trae las mesas de un local.
     *
     * @param localId el id del local
     * @return las mesas de ese local
     */
    List<MesaLocales> findByLocalId(Long localId);

    /**
     * Trae las mesas que están en un estado dado.
     *
     * @param estado el estado por el que se filtra
     * @return las mesas en ese estado
     */
    List<MesaLocales> findByEstado(Estado estado);
}
