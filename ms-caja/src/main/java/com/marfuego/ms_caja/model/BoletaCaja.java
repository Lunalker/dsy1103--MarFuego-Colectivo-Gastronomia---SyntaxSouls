package com.marfuego.ms_caja.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidad que representa una boleta emitida por caja. Guarda el id del pedido,
 * que es lo que hace funcionar la regla R4 (saber si un pedido fue pagado).
 */
@Entity
@Data
@NoArgsConstructor

@Schema(
        name = "Boleta",
        description = "Representa la boleta del sistema"
)

public class BoletaCaja {


    @Schema(
            description = "Identificador único",
            example = "1"
    )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Id del pedido al que pertenece la boleta (R4: para validar pago del delivery)

    @Schema(
            description = "Id del pedido asociado a la boleta (R4: para validar el pago del delivery)",
            example = "1"
    )

    private Long pedidoId;

    @Schema(
            description = "Nombre completo del cliente",
            example = "victor espada"
    )
    @NotBlank(message = "El Campo no puede ir vacio")
    private String nombreCliente;


    @Schema(
            description = "Ingreso del monto total de la boleta",
            example = "10000"
    )
    @Min(value = 0, message = "El monto total no puede ser negativo")
    private Double montoTotal;

    @Schema(
            description = "Fecha de creacion de la boleta",
            example = "2026-05-11"
    )
    @PastOrPresent(message = "La fecha introducida debe ser valida")
    private LocalDate fecha;

    @Schema(
            description = "Metodo de pago con el que se opera ",
            example = "EFECTIVO"
    )

    @Enumerated(EnumType.STRING)
    @NotNull
    private MetodoPago metodoPago;

    public BoletaCaja(Long id, Long pedidoId, String nombreCliente, Double montoTotal, LocalDate fecha, MetodoPago metodoPago) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.nombreCliente = nombreCliente;
        this.montoTotal = montoTotal;
        this.fecha = fecha;
        this.metodoPago = metodoPago;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }
}
