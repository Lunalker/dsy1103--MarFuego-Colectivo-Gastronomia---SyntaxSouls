package com.marfuego.ms_caja.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class BoletaCaja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Id del pedido al que pertenece la boleta (R4: para validar pago del delivery)
    private Long pedidoId;

    @NotBlank(message = "El Campo no puede ir vacio")
    private String nombreCliente;

    @Min(value = 0, message = "El monto total no puede ser negativo")
    private Double montoTotal;

    @PastOrPresent(message = "La fecha introducida debe ser valida")
    private LocalDate fecha;

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
