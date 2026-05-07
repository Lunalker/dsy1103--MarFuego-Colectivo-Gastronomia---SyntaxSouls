package com.marfuego.ms_inventario.service;


import com.marfuego.ms_inventario.model.InventarioModel;
import com.marfuego.ms_inventario.repository.InventarioRepository;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import java.util.List;

@Service
public class InventarioService {
    private final InventarioRepository repository;

    public InventarioService(InventarioRepository repository){
        this.repository = repository;
    }
    public List<InventarioModel> obtenerTodoInventario(){
        return repository.findAll();
    }
    public InventarioModel obtenerInventarioPorId(long id){
        return repository.findById(id).orElse(null);
    }
    public InventarioModel guardarInventario(InventarioModel inventarioModel){
        return repository.save(inventarioModel);
    }
    public void borrarInventario(Long id){
        repository.deleteById(id);
    }
}
