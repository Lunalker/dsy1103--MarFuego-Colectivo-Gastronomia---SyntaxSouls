package com.marfuego.ms_locales.repository;

import com.marfuego.ms_locales.model.Estado;
import com.marfuego.ms_locales.model.MesaLocales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesaRepositoryLocales extends JpaRepository<MesaLocales, Long> {

    List<MesaLocales> findByLocalId(Long localId);

    List<MesaLocales> findByEstado(Estado estado);
}
