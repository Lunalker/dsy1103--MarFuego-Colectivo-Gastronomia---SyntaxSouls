package com.marfuego.ms_pedidos.service;

import com.marfuego.ms_pedidos.client.MenuClient;
import com.marfuego.ms_pedidos.dto.DetallePedidoRequestDTO;
import com.marfuego.ms_pedidos.dto.DetallePedidoResponseDTO;
import com.marfuego.ms_pedidos.dto.PedidoRequestDTO;
import com.marfuego.ms_pedidos.dto.PedidoResponseDTO;
import com.marfuego.ms_pedidos.exception.NegocioException;
import com.marfuego.ms_pedidos.exception.RecursoNoEncontradoException;
import com.marfuego.ms_pedidos.model.DetallePedido;
import com.marfuego.ms_pedidos.model.Pedido;
import com.marfuego.ms_pedidos.repository.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private MenuClient menuClient;

    public List<PedidoResponseDTO> listarTodos() {
        List<Pedido> pedidos = repository.findAll();
        List<PedidoResponseDTO> resultado = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            resultado.add(toDTO(pedido));
        }
        return resultado;
    }

    public List<PedidoResponseDTO> listarPorLocal(Long localId) {
        List<Pedido> pedidos = repository.findByLocalId(localId);
        List<PedidoResponseDTO> resultado = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            resultado.add(toDTO(pedido));
        }
        return resultado;
    }

    public List<PedidoResponseDTO> listarPorEstado(String estado) {
        List<Pedido> pedidos = repository.findByEstado(estado);
        List<PedidoResponseDTO> resultado = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            resultado.add(toDTO(pedido));
        }
        return resultado;
    }

    public PedidoResponseDTO obtenerPorId(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Pedido con id " + id + " no encontrado"));
        return toDTO(pedido);
    }

    public PedidoResponseDTO crear(PedidoRequestDTO dto) {
        log.info("Creando pedido tipo {} en local {}", dto.getTipo(), dto.getLocalId());

        // Un pedido tipo COMEDOR debe tener mesaId
        if ("COMEDOR".equals(dto.getTipo()) && dto.getMesaId() == null) {
            throw new NegocioException("Un pedido tipo COMEDOR debe indicar mesaId");
        }

        // R1: verificar que todos los platos esten disponibles (llama a ms-menu)
        for (DetallePedidoRequestDTO detalle : dto.getDetalles()) {
            if (!menuClient.platoDisponible(detalle.getPlatoId())) {
                throw new NegocioException(
                        "El plato con id " + detalle.getPlatoId() + " no esta disponible");
            }
        }

        Pedido pedido = new Pedido();
        pedido.setMesaId(dto.getMesaId());
        pedido.setLocalId(dto.getLocalId());
        pedido.setTipo(dto.getTipo());
        pedido.setEstado("PENDIENTE");
        pedido.setFecha(LocalDateTime.now());

        double total = 0.0;
        for (DetallePedidoRequestDTO detalleDTO : dto.getDetalles()) {
            DetallePedido detalle = new DetallePedido();
            detalle.setPlatoId(detalleDTO.getPlatoId());
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());
            detalle.setPedido(pedido);
            pedido.getDetalles().add(detalle);
            total += detalleDTO.getCantidad() * detalleDTO.getPrecioUnitario();
        }
        pedido.setTotal(total);

        Pedido guardado = repository.save(pedido);
        return toDTO(guardado);
    }

    public PedidoResponseDTO actualizar(Long id, PedidoRequestDTO dto) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Pedido con id " + id + " no encontrado"));

        // Un pedido tipo COMEDOR debe tener mesaId
        if ("COMEDOR".equals(dto.getTipo()) && dto.getMesaId() == null) {
            throw new NegocioException("Un pedido tipo COMEDOR debe indicar mesaId");
        }

        pedido.setMesaId(dto.getMesaId());
        pedido.setLocalId(dto.getLocalId());
        pedido.setTipo(dto.getTipo());

        // Limpiamos los detalles viejos y agregamos los nuevos
        pedido.getDetalles().clear();
        double total = 0.0;
        for (DetallePedidoRequestDTO detalleDTO : dto.getDetalles()) {
            DetallePedido detalle = new DetallePedido();
            detalle.setPlatoId(detalleDTO.getPlatoId());
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());
            detalle.setPedido(pedido);
            pedido.getDetalles().add(detalle);
            total += detalleDTO.getCantidad() * detalleDTO.getPrecioUnitario();
        }
        pedido.setTotal(total);

        return toDTO(repository.save(pedido));
    }

    public PedidoResponseDTO cambiarEstado(Long id, String nuevoEstado) {
        if (!nuevoEstado.matches("PENDIENTE|EN_PREPARACION|ENTREGADO|CANCELADO")) {
            throw new NegocioException(
                    "Estado invalido. Debe ser PENDIENTE, EN_PREPARACION, ENTREGADO o CANCELADO");
        }

        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Pedido con id " + id + " no encontrado"));

        pedido.setEstado(nuevoEstado);
        return toDTO(repository.save(pedido));
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Pedido con id " + id + " no encontrado");
        }
        repository.deleteById(id);
    }

    private PedidoResponseDTO toDTO(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setMesaId(pedido.getMesaId());
        dto.setLocalId(pedido.getLocalId());
        dto.setEstado(pedido.getEstado());
        dto.setTipo(pedido.getTipo());
        dto.setFecha(pedido.getFecha());
        dto.setTotal(pedido.getTotal());

        List<DetallePedidoResponseDTO> detallesDTO = new ArrayList<>();
        for (DetallePedido detalle : pedido.getDetalles()) {
            DetallePedidoResponseDTO detalleDTO = new DetallePedidoResponseDTO();
            detalleDTO.setId(detalle.getId());
            detalleDTO.setPlatoId(detalle.getPlatoId());
            detalleDTO.setCantidad(detalle.getCantidad());
            detalleDTO.setPrecioUnitario(detalle.getPrecioUnitario());
            detalleDTO.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());
            detallesDTO.add(detalleDTO);
        }
        dto.setDetalles(detallesDTO);
        return dto;
    }
}
