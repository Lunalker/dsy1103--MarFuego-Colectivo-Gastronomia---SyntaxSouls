package com.marfuego.ms_reportes.dto.reporte;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Reporte que junta los datos de un local: su info, sus platos y números de sus pedidos.
 */
@Data
@Schema(
        name = "ResumenLocalDTO",
        description = "Reporte consolidado de un local (locales + menu + pedidos)")
public class ResumenLocalDTO {

    @Schema(
            description = "Id del local",
            example = "1")
    private Long localId;

    @Schema(
            description = "Nombre del local",
            example = "MarFuego Pelluco")
    private String nombreLocal;

    @Schema(
            description = "Ubicacion del local",
            example = "PELLUCO")
    private String ubicacion;

    @Schema(
            description = "Total de platos del local",
            example = "25")
    private Integer totalPlatos;

    @Schema(
            description = "Platos disponibles del local (R1)",
            example = "20")
    private Integer platosDisponibles;

    @Schema(
            description = "Total de pedidos del local",
            example = "120")
    private Integer totalPedidos;

    @Schema(
            description = "Pedidos pendientes del local",
            example = "8")
    private Integer pedidosPendientes;

    @Schema(
            description = "Ventas totales del local",
            example = "1599000.0")
    private Double ventasTotales;
}
