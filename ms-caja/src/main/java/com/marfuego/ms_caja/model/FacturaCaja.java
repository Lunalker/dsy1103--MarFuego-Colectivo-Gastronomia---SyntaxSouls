package com.marfuego.ms_caja.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
public class FacturaCaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String razonSocial;

    @NotBlank
    private String rut;

    @Min(value = 0, message = "El monto total no puede ser negativo")
    private Double montoTotal;

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
