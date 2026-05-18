package com.marfuego.ms_caja.controller;

import com.marfuego.ms_caja.model.BoletaCaja;
import com.marfuego.ms_caja.model.FacturaCaja;
import com.marfuego.ms_caja.service.CajaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/caja")
public class CajaController {

    private final CajaService service;

    public CajaController(CajaService service) {
        this.service = service;
    }

    //Aca esta lo que tenga que ver con las boletas

    @GetMapping("/boletas")
    public List<BoletaCaja> listarBoletas() {
        return service.listarBoletas();
    }

    @GetMapping("/boletas/{id}")
    public BoletaCaja buscarBoletaPorId(@PathVariable Long id) {
        return service.buscarBoletaPorId(id);
    }

    // R4: buscar la boleta de un pedido (lo va a usar ms-pedidos)
    @GetMapping("/boletas/pedido/{pedidoId}")
    public BoletaCaja buscarBoletaPorPedido(@PathVariable Long pedidoId) {
        return service.buscarBoletaPorPedido(pedidoId);
    }

    @PostMapping("/boletas")
    public BoletaCaja guardarBoleta(@Valid @RequestBody BoletaCaja boleta) {
        return service.guardarBoleta(boleta);
    }

    //Aca esta lo que tenga que ver con las facturas

    @GetMapping("/facturas")
    public List<FacturaCaja> listarFacturas() {
        return service.listarFacturas();
    }

    @GetMapping("/facturas/{id}")
    public FacturaCaja buscarFacturaPorId(@PathVariable Long id) {
        return service.buscarFacturaPorId(id);
    }

    @PostMapping("/facturas")
    public FacturaCaja guardarFactura(@Valid @RequestBody FacturaCaja factura) {
        return service.guardarFactura(factura);
    }
}