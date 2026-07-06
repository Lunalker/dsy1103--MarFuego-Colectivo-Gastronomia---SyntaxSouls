package com.marfuego.ms_caja.service;

import com.marfuego.ms_caja.model.BoletaCaja;
import com.marfuego.ms_caja.model.FacturaCaja;
import com.marfuego.ms_caja.repository.BoletaRepository;
import com.marfuego.ms_caja.repository.FacturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Aquí está la lógica de caja. Se encarga de guardar y buscar boletas y
 * facturas, y da soporte a la regla R4 (buscar la boleta de un pedido para
 * saber si ya fue pagado).
 */
@Service

public class CajaService {

    private final BoletaRepository boletaRepository;
    private final FacturaRepository facturaRepository;

    public CajaService(
            BoletaRepository boletaRepository,
            FacturaRepository facturaRepository) {
        this.boletaRepository = boletaRepository;
        this.facturaRepository = facturaRepository;
    }

    // ===== Boletas =====

    /**
     * Devuelve todas las boletas que hay guardadas.
     *
     * @return la lista de boletas
     */
    public List<BoletaCaja> listarBoletas() {
        return boletaRepository.findAll();
    }

    /**
     * Guarda una boleta (la crea si es nueva o la actualiza si ya existía).
     *
     * @param boleta la boleta que se quiere guardar
     * @return la boleta ya guardada
     */
    public BoletaCaja guardarBoleta(BoletaCaja boleta) {
        return boletaRepository.save(boleta);
    }

    /**
     * Busca una boleta por su id. Si no existe, devuelve null.
     *
     * @param id el id de la boleta
     * @return la boleta encontrada, o null si no existe
     */
    public BoletaCaja buscarBoletaPorId(Long id) {
        return boletaRepository.findById(id).orElse(null);
    }

    /**
     * Busca la boleta que corresponde a un pedido (regla R4), para poder saber
     * si ese pedido ya fue pagado.
     *
     * @param pedidoId el id del pedido
     * @return la boleta de ese pedido, o null si todavía no existe
     */
    public BoletaCaja buscarBoletaPorPedido(Long pedidoId) {
        return boletaRepository.findByPedidoId(pedidoId);
    }

    // ===== Facturas =====

    /**
     * Devuelve todas las facturas que hay guardadas.
     *
     * @return la lista de facturas
     */
    public List<FacturaCaja> listarFacturas() {
        return facturaRepository.findAll();
    }

    /**
     * Guarda una factura (la crea si es nueva o la actualiza si ya existía).
     *
     * @param factura la factura que se quiere guardar
     * @return la factura ya guardada
     */
    public FacturaCaja guardarFactura(FacturaCaja factura) {
        return facturaRepository.save(factura);
    }

    /**
     * Busca una factura por su id. Si no existe, devuelve null.
     *
     * @param id el id de la factura
     * @return la factura encontrada, o null si no existe
     */
    public FacturaCaja buscarFacturaPorId(Long id) {
        return facturaRepository.findById(id).orElse(null);
    }
}