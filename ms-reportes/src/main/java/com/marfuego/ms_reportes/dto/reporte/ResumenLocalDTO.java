package com.marfuego.ms_reportes.dto.reporte;

import lombok.Data;

// Reporte que consolida info de un local: cuantos platos, cuantos pedidos, cuanto vendio.
// Este reporte se arma llamando a 3 microservicios (locales + menu + pedidos).
@Data
public class ResumenLocalDTO {
    private Long localId;
    private String nombreLocal;
    private String ubicacion;
    private Integer totalPlatos;
    private Integer platosDisponibles;
    private Integer totalPedidos;
    private Integer pedidosPendientes;
    private Double ventasTotales;
}
