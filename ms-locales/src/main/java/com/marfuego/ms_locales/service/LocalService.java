package com.marfuego.ms_locales.service;

import com.marfuego.ms_locales.model.Local;
import com.marfuego.ms_locales.model.Ubicacion;
import com.marfuego.ms_locales.repository.LocalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Logica de negocio para las sucursales de MarFuego
@Service
@Slf4j
public class LocalService {

    @Autowired
    private LocalRepository localRepository;

    // 1. Listar todas las sucursales
    public List<Local> listarTodos() {
        log.info("Listando todos los locales");
        return localRepository.findAll();
    }

    // 2. buscar local por su id
    public Local buscarPorId(Long id) {
        log.info("Buscando local con su id {}", id);
        return localRepository.findById(id).orElse(null);
    }

    //3. Guardar un local
    public Local guardar(Local local) {
        log.info("Guardando nuevo local: {}", local.getNombre());

        // local creado ya esta sincronizado
        local.setSincronizadoDisponible(true);

        return localRepository.save(local);
    }

    // 4. actualizar datos de un local
    public Local actualizar(Long id, Local localActualizado) {
        log.info("Actualizando el local: {}", id);

        Local local = buscarPorId(id);

        local.setNombre(localActualizado.getNombre());
        local.setDescripcion(localActualizado.getDescripcion());
        local.setDireccion(localActualizado.getDireccion());
        local.setTelefono(localActualizado.getTelefono());
        local.setCapacidadMesas(localActualizado.getCapacidadMesas());
        local.setEstaAbierto(localActualizado.isEstaAbierto());

        return localRepository.save(local);
    }

    // 5. eliminar local
    public void eliminar(Long id) {
        log.warn("Eliminando el local con su id {}", id);
            if(!localRepository.existsById(id)){
                log.error("El local con su id {} no existe", id);
            }
            localRepository.deleteById(id);
    }


    // 6. Buscar local por ubicacion
    public List<Local> buscarPorUbicacion(Ubicacion ubicacion) {
        log.info("Buscando locales por ubicacion:  {}", ubicacion);
        return localRepository.findByUbicacion(ubicacion);
    }

}
