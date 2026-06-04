package com.marfuego.ms_caja.controller;

import com.marfuego.ms_caja.model.BoletaCaja;
import com.marfuego.ms_caja.model.FacturaCaja;
import com.marfuego.ms_caja.service.CajaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/caja")


@Tag(
        name = "Caja",
        description = "Operaciones relacionadas con la caja"
)

public class CajaController {

    private final CajaService service;

    public CajaController(CajaService service) {
        this.service = service;
    }

    //Aca esta lo que tenga que ver con las boletas

    @Operation(
            summary = "Listar Boletas",
            description = "Obtiene todas las boletas registradas")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa"
            ),
            @ApiResponse( responseCode = "404",
                    description = "Recurso no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/boletas")
    public List<BoletaCaja> listarBoletas() {
        return service.listarBoletas();
    }

    @Operation(
            summary = "Listar boletas por su id ",
            description = "Obtiene la boletas por su id correspondiente")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa"
            ),
            @ApiResponse( responseCode = "404",
                    description = "Recurso no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/boletas/{id}")
    public BoletaCaja buscarBoletaPorId(@PathVariable Long id) {
        return service.buscarBoletaPorId(id);
    }

    // R4: buscar la boleta de un pedido (lo va a usar ms-pedidos)
    @Operation(
            summary = "Buscar la boleta por pedido",
            description = "Obtiene boleta por el pedido correspondiente")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa"
            ),
            @ApiResponse( responseCode = "404",
                    description = "Recurso no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/boletas/pedido/{pedidoId}")
    public BoletaCaja buscarBoletaPorPedido(@PathVariable Long pedidoId) {
        return service.buscarBoletaPorPedido(pedidoId);
    }


    @Operation(
            summary = "guardar Boletas",
            description = "Guarda las boletas")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa"
            ),
            @ApiResponse( responseCode = "404",
                    description = "Recurso no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })

    @PostMapping("/boletas")
    public BoletaCaja guardarBoleta(@Valid @RequestBody BoletaCaja boleta) {
        return service.guardarBoleta(boleta);
    }

    //Aca esta lo que tenga que ver con las facturas

    @Operation(
            summary = "Listar Facturas",
            description = "Obtiene todas las facturas registradas")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa"
            ),
            @ApiResponse( responseCode = "404",
                    description = "Recurso no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })

    @GetMapping("/facturas")
    public List<FacturaCaja> listarFacturas() {
        return service.listarFacturas();
    }

    @Operation(
            summary = "Buscar factura por id",
            description = "Obtiene la factura con su id correspondiente")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa"
            ),
            @ApiResponse( responseCode = "404",
                    description = "Recurso no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/facturas/{id}")
    public FacturaCaja buscarFacturaPorId(@PathVariable Long id) {
        return service.buscarFacturaPorId(id);
    }


    @Operation(
            summary = "guardar Facturas",
            description = "Guarda las Facturas")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa"
            ),
            @ApiResponse( responseCode = "404",
                    description = "Recurso no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @PostMapping("/facturas")
    public FacturaCaja guardarFactura(@Valid @RequestBody FacturaCaja factura) {
        return service.guardarFactura(factura);
    }
}