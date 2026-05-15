package com.marfuego.ms_inventario.service;

import com.marfuego.ms_inventario.model.IngredienteInventario;
import com.marfuego.ms_inventario.repository.RepositoryInventario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServiceInventario {
    @Autowired
    private RepositoryInventario repository;

    public List<IngredienteInventario> listarTodo() {
        return repository.findAll();
    }

    public IngredienteInventario guardar(IngredienteInventario item) {
        return repository.save(item);
    }
}