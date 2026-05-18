package com.marfuego.ms_caja.service;

import com.marfuego.ms_caja.model.BoletaCaja;
import com.marfuego.ms_caja.model.FacturaCaja;
import com.marfuego.ms_caja.repository.BoletaRepository;
import com.marfuego.ms_caja.repository.FacturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    //Aca esta lo que tenga que ver con las boletas
    public List<BoletaCaja> listarBoletas() {
        return boletaRepository.findAll();
    }

    public BoletaCaja guardarBoleta(BoletaCaja boleta) {
        return boletaRepository.save(boleta);
    }

    public BoletaCaja buscarBoletaPorId(Long id) {
        return boletaRepository.findById(id).orElse(null);
    }

    // R4: trae la boleta de un pedido, para saber si fue pagado
    public BoletaCaja buscarBoletaPorPedido(Long pedidoId) {
        return boletaRepository.findByPedidoId(pedidoId);
    }

    //Aca esta lo que tenga que ver con las facturas
    public List<FacturaCaja> listarFacturas() {
        return facturaRepository.findAll();
    }

    public FacturaCaja guardarFactura(FacturaCaja factura) {
        return facturaRepository.save(factura);
    }

    public FacturaCaja buscarFacturaPorId(Long id) {
        return facturaRepository.findById(id).orElse(null);
    }
}