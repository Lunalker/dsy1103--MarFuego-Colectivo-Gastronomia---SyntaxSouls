package com.marfuego.ms_locales.repository;

import com.marfuego.ms_locales.model.LocalLocales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de los locales. Extiende de JpaRepository, así que ya trae los
 * métodos típicos para guardar, buscar y borrar.
 */
@Repository
public interface LocalRepositoryLocales extends JpaRepository<LocalLocales, Long> {
}
