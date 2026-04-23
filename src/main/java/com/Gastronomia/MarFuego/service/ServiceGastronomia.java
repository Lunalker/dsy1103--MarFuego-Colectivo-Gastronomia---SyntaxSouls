package com.Gastronomia.MarFuego.service;

import com.Gastronomia.MarFuego.model.PlatoGastronomia;
import com.Gastronomia.MarFuego.repository.RepositoryGastronomia;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ServiceGastronomia {

    @Autowired
    private RepositoryGastronomia platoRepo;

    public PlatoGastronomia procesarReglasPlato(PlatoGastronomia plato) {
        // R5: Margen mínimo
        if (!plato.cumpleMargenMinimo()) {
            log.warn("ALERTA: El plato {} no cumple con el markup del 300%", plato.getNombre());
        }

        // R1: Por defecto, si se crea, lo dejamos disponible (o según lógica)
        if (plato.getDisponible() == null) {
            plato.setDisponible(true);
        }

        return platoRepo.save(plato);
    }
}

