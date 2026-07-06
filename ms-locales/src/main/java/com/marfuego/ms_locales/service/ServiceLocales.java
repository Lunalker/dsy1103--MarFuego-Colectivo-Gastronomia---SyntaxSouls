package com.marfuego.ms_locales.service;

import com.marfuego.ms_locales.dto.*;
import com.marfuego.ms_locales.exception.NegocioException;
import com.marfuego.ms_locales.exception.RecursoNoEncontradoException;
import com.marfuego.ms_locales.model.Estado;
import com.marfuego.ms_locales.model.LocalLocales;
import com.marfuego.ms_locales.model.MesaLocales;
import com.marfuego.ms_locales.repository.LocalRepositoryLocales;
import com.marfuego.ms_locales.repository.MesaRepositoryLocales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Aquí está la lógica de locales y mesas. Lo más importante es la regla R3: una
 * mesa OCUPADA no se puede reservar, y al cerrar la cuenta pasa a LIMPIEZA por
 * unos minutos antes de volver a quedar LIBRE.
 */
@Service
public class ServiceLocales {

    private static final Logger log = LoggerFactory.getLogger(ServiceLocales.class);

    // R3: tiempo de limpieza obligatorio antes de pasar a LIBRE
    private static final int MINUTOS_LIMPIEZA = 15;

    @Autowired
    private LocalRepositoryLocales localRepo;

    @Autowired
    private MesaRepositoryLocales mesaRepo;

    // ===== LOCALES =====

    /**
     * Devuelve todos los locales registrados.
     *
     * @return la lista de locales
     */
    public List<LocalResponseDTO> listarLocales() {
        List<LocalLocales> locales = localRepo.findAll();
        List<LocalResponseDTO> resultado = new ArrayList<>();
        for (LocalLocales local : locales) {
            resultado.add(toLocalDTO(local));
        }
        return resultado;
    }

    /**
     * Busca un local por su id.
     *
     * @param id el id del local
     * @return el local encontrado
     * @throws RecursoNoEncontradoException si el local no existe
     */
    public LocalResponseDTO obtenerLocal(Long id) {
        LocalLocales local = localRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Local con id " + id + " no encontrado"));
        return toLocalDTO(local);
    }

    /**
     * Crea un local nuevo con los datos del DTO.
     *
     * @param dto los datos del local
     * @return el local ya creado, con su id
     */
    public LocalResponseDTO crearLocal(LocalRequestDTO dto) {
        log.info("Creando local: {}", dto.getNombre());
        LocalLocales local = new LocalLocales();
        local.setNombre(dto.getNombre());
        local.setDireccion(dto.getDireccion());
        local.setUbicacion(dto.getUbicacion());
        return toLocalDTO(localRepo.save(local));
    }

    /**
     * Actualiza un local que ya existe con los datos nuevos.
     *
     * @param id  el id del local a modificar
     * @param dto los datos nuevos
     * @return el local actualizado
     * @throws RecursoNoEncontradoException si el local no existe
     */
    public LocalResponseDTO actualizarLocal(Long id, LocalRequestDTO dto) {
        LocalLocales local = localRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Local con id " + id + " no encontrado"));
        local.setNombre(dto.getNombre());
        local.setDireccion(dto.getDireccion());
        local.setUbicacion(dto.getUbicacion());
        return toLocalDTO(localRepo.save(local));
    }

    /**
     * Borra un local según su id.
     *
     * @param id el id del local a eliminar
     * @throws RecursoNoEncontradoException si el local no existe
     */
    public void eliminarLocal(Long id) {
        if (!localRepo.existsById(id)) {
            throw new RecursoNoEncontradoException("Local con id " + id + " no encontrado");
        }
        localRepo.deleteById(id);
    }

    // ===== MESAS =====

    /**
     * Devuelve todas las mesas. Antes de devolverlas revisa si a alguna en
     * LIMPIEZA ya le tocó volver a LIBRE (regla R3).
     *
     * @return la lista de mesas
     */
    public List<MesaResponseDTO> listarMesas() {
        List<MesaLocales> mesas = mesaRepo.findAll();
        List<MesaResponseDTO> resultado = new ArrayList<>();
        for (MesaLocales mesa : mesas) {
            verificarFinLimpieza(mesa);
            resultado.add(toMesaDTO(mesa));
        }
        return resultado;
    }

    /**
     * Devuelve las mesas de un local, aplicando la revisión de la regla R3.
     *
     * @param localId el id del local
     * @return las mesas de ese local
     */
    public List<MesaResponseDTO> listarMesasPorLocal(Long localId) {
        List<MesaLocales> mesas = mesaRepo.findByLocalId(localId);
        List<MesaResponseDTO> resultado = new ArrayList<>();
        for (MesaLocales mesa : mesas) {
            verificarFinLimpieza(mesa);
            resultado.add(toMesaDTO(mesa));
        }
        return resultado;
    }

    /**
     * Devuelve las mesas que están en un estado dado.
     *
     * @param estado el estado por el que se filtra (LIBRE, RESERVADA, OCUPADA o LIMPIEZA)
     * @return las mesas en ese estado
     */
    public List<MesaResponseDTO> listarMesasPorEstado(Estado estado) {
        List<MesaLocales> mesas = mesaRepo.findByEstado(estado);
        List<MesaResponseDTO> resultado = new ArrayList<>();
        for (MesaLocales mesa : mesas) {
            verificarFinLimpieza(mesa);
            resultado.add(toMesaDTO(mesa));
        }
        return resultado;
    }

    /**
     * Busca una mesa por su id, revisando de paso la regla R3.
     *
     * @param id el id de la mesa
     * @return la mesa encontrada
     * @throws RecursoNoEncontradoException si la mesa no existe
     */
    public MesaResponseDTO obtenerMesa(Long id) {
        MesaLocales mesa = mesaRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Mesa con id " + id + " no encontrada"));
        verificarFinLimpieza(mesa);
        return toMesaDTO(mesa);
    }

    /**
     * Crea una mesa dentro de un local. La mesa siempre nace en estado LIBRE.
     *
     * @param dto los datos de la mesa
     * @return la mesa ya creada
     * @throws RecursoNoEncontradoException si el local indicado no existe
     */
    public MesaResponseDTO crearMesa(MesaRequestDTO dto) {
        LocalLocales local = localRepo.findById(dto.getLocalId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Local con id " + dto.getLocalId() + " no encontrado"));

        MesaLocales mesa = new MesaLocales();
        mesa.setNumeroMesa(dto.getNumeroMesa());
        mesa.setCapacidad(dto.getCapacidad());
        mesa.setEstado(Estado.LIBRE); // siempre arranca LIBRE
        mesa.setLocal(local);

        return toMesaDTO(mesaRepo.save(mesa));
    }

    /**
     * Actualiza los datos de una mesa y el local al que pertenece.
     *
     * @param id  el id de la mesa
     * @param dto los datos nuevos de la mesa
     * @return la mesa actualizada
     * @throws RecursoNoEncontradoException si la mesa o el local no existen
     */
    public MesaResponseDTO actualizarMesa(Long id, MesaRequestDTO dto) {
        MesaLocales mesa = mesaRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Mesa con id " + id + " no encontrada"));

        LocalLocales local = localRepo.findById(dto.getLocalId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Local con id " + dto.getLocalId() + " no encontrado"));

        mesa.setNumeroMesa(dto.getNumeroMesa());
        mesa.setCapacidad(dto.getCapacidad());
        mesa.setLocal(local);

        return toMesaDTO(mesaRepo.save(mesa));
    }

    /**
     * Borra una mesa según su id.
     *
     * @param id el id de la mesa a eliminar
     * @throws RecursoNoEncontradoException si la mesa no existe
     */
    public void eliminarMesa(Long id) {
        if (!mesaRepo.existsById(id)) {
            throw new RecursoNoEncontradoException("Mesa con id " + id + " no encontrada");
        }
        mesaRepo.deleteById(id);
    }

    // ===== R3: cambios de estado de mesa =====

    /**
     * Reserva una mesa (regla R3). Solo se puede si la mesa está LIBRE.
     *
     * @param id el id de la mesa
     * @return la mesa ahora en estado RESERVADA
     * @throws NegocioException             si la mesa no está LIBRE
     * @throws RecursoNoEncontradoException si la mesa no existe
     */
    public MesaResponseDTO reservarMesa(Long id) {
        MesaLocales mesa = mesaRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Mesa con id " + id + " no encontrada"));

        if (mesa.getEstado() != Estado.LIBRE) {
            throw new NegocioException(
                    "No se puede reservar: la mesa esta en estado " + mesa.getEstado());
        }

        mesa.setEstado(Estado.RESERVADA);
        return toMesaDTO(mesaRepo.save(mesa));
    }

    /**
     * Marca una mesa como OCUPADA (regla R3). No se puede si ya está ocupada o
     * si está en LIMPIEZA.
     *
     * @param id el id de la mesa
     * @return la mesa ahora en estado OCUPADA
     * @throws NegocioException             si la mesa está OCUPADA o en LIMPIEZA
     * @throws RecursoNoEncontradoException si la mesa no existe
     */
    public MesaResponseDTO ocuparMesa(Long id) {
        MesaLocales mesa = mesaRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Mesa con id " + id + " no encontrada"));

        if (mesa.getEstado() == Estado.OCUPADA) {
            throw new NegocioException("La mesa ya esta ocupada");
        }
        if (mesa.getEstado() == Estado.LIMPIEZA) {
            throw new NegocioException("La mesa esta en limpieza, no se puede ocupar");
        }

        mesa.setEstado(Estado.OCUPADA);
        return toMesaDTO(mesaRepo.save(mesa));
    }

    /**
     * Libera una mesa OCUPADA cuando se cierra la cuenta (regla R3): la pasa a
     * LIMPIEZA y guarda a qué hora empezó, para después poder soltarla.
     *
     * @param id el id de la mesa
     * @return la mesa ahora en estado LIMPIEZA
     * @throws NegocioException             si la mesa no está OCUPADA
     * @throws RecursoNoEncontradoException si la mesa no existe
     */
    public MesaResponseDTO liberarMesa(Long id) {
        MesaLocales mesa = mesaRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Mesa con id " + id + " no encontrada"));

        if (mesa.getEstado() != Estado.OCUPADA) {
            throw new NegocioException(
                    "Solo se puede liberar una mesa OCUPADA (actual: " + mesa.getEstado() + ")");
        }

        mesa.setEstado(Estado.LIMPIEZA);
        mesa.setInicioLimpieza(LocalDateTime.now());
        log.info("Mesa {} en LIMPIEZA por {} min", id, MINUTOS_LIMPIEZA);

        return toMesaDTO(mesaRepo.save(mesa));
    }

    // ===== Auxiliares =====

    /**
     * Revisa si una mesa que está en LIMPIEZA ya cumplió los 15 minutos y, si es
     * así, la vuelve a dejar LIBRE (regla R3).
     *
     * @param mesa la mesa que se está revisando
     */
    private void verificarFinLimpieza(MesaLocales mesa) {
        if (mesa.getEstado() == Estado.LIMPIEZA && mesa.getInicioLimpieza() != null) {
            long minutos = Duration.between(mesa.getInicioLimpieza(), LocalDateTime.now()).toMinutes();
            if (minutos >= MINUTOS_LIMPIEZA) {
                mesa.setEstado(Estado.LIBRE);
                mesa.setInicioLimpieza(null);
                mesaRepo.save(mesa);
            }
        }
    }

    /**
     * Convierte una entidad de local en su DTO de respuesta.
     *
     * @param local la entidad que viene de la base
     * @return el DTO equivalente
     */
    private LocalResponseDTO toLocalDTO(LocalLocales local) {
        LocalResponseDTO dto = new LocalResponseDTO();
        dto.setId(local.getId());
        dto.setNombre(local.getNombre());
        dto.setDireccion(local.getDireccion());
        dto.setUbicacion(local.getUbicacion());
        return dto;
    }

    /**
     * Convierte una entidad de mesa en su DTO de respuesta.
     *
     * @param mesa la entidad que viene de la base
     * @return el DTO equivalente
     */
    private MesaResponseDTO toMesaDTO(MesaLocales mesa) {
        MesaResponseDTO dto = new MesaResponseDTO();
        dto.setId(mesa.getId());
        dto.setNumeroMesa(mesa.getNumeroMesa());
        dto.setCapacidad(mesa.getCapacidad());
        dto.setEstado(mesa.getEstado());
        dto.setLocalId(mesa.getLocal().getId());
        return dto;
    }
}
