package com.marfuego.ms_caja.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * Entidad que representa una factura emitida por caja.
 */
@Entity
@Data
@NoArgsConstructor

@Schema(
        name = "Factura",
        description = "Representa la Factura del sistema"
)

public class FacturaCaja {

    @Schema(
            description = "Identificador único",
            example = "1"
    )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Schema(
            description = "Razon social por el que expide la factura",
            example = "Comercio"
    )
    @NotBlank
    private String razonSocial;


    @Schema(
            description = "Rut completo del cliente",
            example = "76543210-K"
    )
    @NotBlank
    private String rut;

    @Schema(
            description = "Ingreso del monto total de la factura",
            example = "10000"
    )
    @Min(value = 0, message = "El monto total no puede ser negativo")
    private Double montoTotal;



    @Schema(
            description = "Metodo de pago con el que se opera ",
            example = "EFECTIVO"
    )
    @Enumerated(EnumType.STRING)
    @NotNull
    private MetodoPago metodoPago;

    public FacturaCaja(Long id, String razonSocial, String rut, Double total, MetodoPago metodoPago) {
        this.id = id;
        this.razonSocial = razonSocial;
        this.rut = rut;
        this.montoTotal = total;
        this.metodoPago = metodoPago;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }
}
