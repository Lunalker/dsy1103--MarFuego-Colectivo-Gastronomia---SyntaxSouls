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

    public List<LocalResponseDTO> listarLocales() {
        List<LocalLocales> locales = localRepo.findAll();
        List<LocalResponseDTO> resultado = new ArrayList<>();
        for (LocalLocales local : locales) {
            resultado.add(toLocalDTO(local));
        }
        return resultado;
    }

    public LocalResponseDTO obtenerLocal(Long id) {
        LocalLocales local = localRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Local con id " + id + " no encontrado"));
        return toLocalDTO(local);
    }

    public LocalResponseDTO crearLocal(LocalRequestDTO dto) {
        log.info("Creando local: {}", dto.getNombre());
        LocalLocales local = new LocalLocales();
        local.setNombre(dto.getNombre());
        local.setDireccion(dto.getDireccion());
        local.setUbicacion(dto.getUbicacion());
        return toLocalDTO(localRepo.save(local));
    }

    public LocalResponseDTO actualizarLocal(Long id, LocalRequestDTO dto) {
        LocalLocales local = localRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Local con id " + id + " no encontrado"));
        local.setNombre(dto.getNombre());
        local.setDireccion(dto.getDireccion());
        local.setUbicacion(dto.getUbicacion());
        return toLocalDTO(localRepo.save(local));
    }

    public void eliminarLocal(Long id) {
        if (!localRepo.existsById(id)) {
            throw new RecursoNoEncontradoException("Local con id " + id + " no encontrado");
        }
        localRepo.deleteById(id);
    }

    // ===== MESAS =====

    public List<MesaResponseDTO> listarMesas() {
        List<MesaLocales> mesas = mesaRepo.findAll();
        List<MesaResponseDTO> resultado = new ArrayList<>();
        for (MesaLocales mesa : mesas) {
            verificarFinLimpieza(mesa);
            resultado.add(toMesaDTO(mesa));
        }
        return resultado;
    }

    public List<MesaResponseDTO> listarMesasPorLocal(Long localId) {
        List<MesaLocales> mesas = mesaRepo.findByLocalId(localId);
        List<MesaResponseDTO> resultado = new ArrayList<>();
        for (MesaLocales mesa : mesas) {
            verificarFinLimpieza(mesa);
            resultado.add(toMesaDTO(mesa));
        }
        return resultado;
    }

    // Lista las mesas que estan en un estado dado (LIBRE, RESERVADA, OCUPADA, LIMPIEZA)
    public List<MesaResponseDTO> listarMesasPorEstado(Estado estado) {
        List<MesaLocales> mesas = mesaRepo.findByEstado(estado);
        List<MesaResponseDTO> resultado = new ArrayList<>();
        for (MesaLocales mesa : mesas) {
            verificarFinLimpieza(mesa);
            resultado.add(toMesaDTO(mesa));
        }
        return resultado;
    }

    public MesaResponseDTO obtenerMesa(Long id) {
        MesaLocales mesa = mesaRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Mesa con id " + id + " no encontrada"));
        verificarFinLimpieza(mesa);
        return toMesaDTO(mesa);
    }

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

    public void eliminarMesa(Long id) {
        if (!mesaRepo.existsById(id)) {
            throw new RecursoNoEncontradoException("Mesa con id " + id + " no encontrada");
        }
        mesaRepo.deleteById(id);
    }

    // ===== R3: cambios de estado de mesa =====

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

    // R3: al cerrar la boleta la mesa pasa a LIMPIEZA por 15 min
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

    // R3: si pasaron 15 min, la mesa en LIMPIEZA vuelve a LIBRE
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

    private LocalResponseDTO toLocalDTO(LocalLocales local) {
        LocalResponseDTO dto = new LocalResponseDTO();
        dto.setId(local.getId());
        dto.setNombre(local.getNombre());
        dto.setDireccion(local.getDireccion());
        dto.setUbicacion(local.getUbicacion());
        return dto;
    }

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
