package com.marfuego.ms_locales.repository;

import com.marfuego.ms_locales.model.LocalLocales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalRepositoryLocales extends JpaRepository<LocalLocales, Long> {
    // Aquí no va nada más, la interfaz debe estar sola en este archivo
}