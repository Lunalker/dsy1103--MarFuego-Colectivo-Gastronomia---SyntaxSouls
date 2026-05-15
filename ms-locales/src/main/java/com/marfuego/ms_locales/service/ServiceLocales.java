package com.marfuego.ms_locales.service;

import com.marfuego.ms_locales.model.LocalLocales;
import com.marfuego.ms_locales.repository.LocalRepositoryLocales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServiceLocales {

    @Autowired
    private LocalRepositoryLocales repository;

    public List<LocalLocales> listarTodos() {
        return repository.findAll();
    }

    public LocalLocales guardar(LocalLocales local) {
        return repository.save(local);
    }
}