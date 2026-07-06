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

/**
 * Aquí está la lógica de los pedidos. Calcula los totales, valida cosas (por
 * ejemplo que un pedido COMEDOR tenga mesa) y aplica la regla R1 cruzada, que
 * consulta a ms-menu con el MenuClient antes de aceptar el pedido.
 */
@Service
public class PedidoService {

    private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private MenuClient menuClient;

    /**
     * Devuelve todos los pedidos registrados.
     *
     * @return la lista de pedidos
     */
    public List<PedidoResponseDTO> listarTodos() {
        List<Pedido> pedidos = repository.findAll();
        List<PedidoResponseDTO> resultado = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            resultado.add(toDTO(pedido));
        }
        return resultado;
    }

    /**
     * Devuelve los pedidos de un local.
     *
     * @param localId el id del local
     * @return los pedidos de ese local
     */
    public List<PedidoResponseDTO> listarPorLocal(Long localId) {
        List<Pedido> pedidos = repository.findByLocalId(localId);
        List<PedidoResponseDTO> resultado = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            resultado.add(toDTO(pedido));
        }
        return resultado;
    }

    /**
     * Devuelve los pedidos que están en un estado dado.
     *
     * @param estado el estado por el que se filtra (PENDIENTE, EN_PREPARACION, ENTREGADO o CANCELADO)
     * @return los pedidos en ese estado
     */
    public List<PedidoResponseDTO> listarPorEstado(String estado) {
        List<Pedido> pedidos = repository.findByEstado(estado);
        List<PedidoResponseDTO> resultado = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            resultado.add(toDTO(pedido));
        }
        return resultado;
    }

    /**
     * Busca un pedido por su id.
     *
     * @param id el id del pedido
     * @return el pedido encontrado
     * @throws RecursoNoEncontradoException si el pedido no existe
     */
    public PedidoResponseDTO obtenerPorId(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Pedido con id " + id + " no encontrado"));
        return toDTO(pedido);
    }

    /**
     * Crea un pedido nuevo y calcula su total a partir de los detalles. Antes
     * revisa que si es COMEDOR tenga mesa, y con el MenuClient que todos los
     * platos estén disponibles en ms-menu (regla R1).
     *
     * @param dto los datos del pedido y sus detalles
     * @return el pedido ya creado, con su id y su total
     * @throws NegocioException si falta la mesa en un COMEDOR o algún plato no está disponible
     */
    public PedidoResponseDTO crear(PedidoRequestDTO dto) {
        log.info("Creando pedido tipo {} en local {}", dto.getTipo(), dto.getLocalId());

        // Un pedido tipo COMEDOR debe tener mesaId
        if ("COMEDOR".equals(dto.getTipo()) && dto.getMesaId() == null) {
            throw new NegocioException("Un pedido tipo COMEDOR debe indicar mesaId");
        }

        // R1: verificar que todos los platos estén disponibles (llama a ms-menu)
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

    /**
     * Actualiza un pedido que ya existe, rehaciendo sus detalles y su total.
     *
     * @param id  el id del pedido
     * @param dto los datos nuevos del pedido
     * @return el pedido actualizado
     * @throws NegocioException             si falta la mesa en un pedido COMEDOR
     * @throws RecursoNoEncontradoException si el pedido no existe
     */
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

    /**
     * Cambia el estado de un pedido, revisando que sea uno de los estados válidos.
     *
     * @param id          el id del pedido
     * @param nuevoEstado el estado nuevo (PENDIENTE, EN_PREPARACION, ENTREGADO o CANCELADO)
     * @return el pedido con el estado actualizado
     * @throws NegocioException             si el estado no es válido
     * @throws RecursoNoEncontradoException si el pedido no existe
     */
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

    /**
     * Borra un pedido según su id.
     *
     * @param id el id del pedido a eliminar
     * @throws RecursoNoEncontradoException si el pedido no existe
     */
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Pedido con id " + id + " no encontrado");
        }
        repository.deleteById(id);
    }

    /**
     * Convierte un pedido (con todos sus detalles) en su DTO de respuesta,
     * calculando el subtotal de cada línea.
     *
     * @param pedido la entidad que viene de la base
     * @return el DTO equivalente para devolver al cliente
     */
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
