package com.marfuego.ms_locales.repository;

import com.marfuego.ms_locales.model.LocalLocales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface repositoryLocales extends JpaRepository<LocalLocales, Long> {
    // Asegúrate de que NO haya otra clase "public class" aquí abajo
}