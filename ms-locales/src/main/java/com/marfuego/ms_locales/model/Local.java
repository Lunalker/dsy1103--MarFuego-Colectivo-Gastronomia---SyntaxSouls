package com.marfuego.ms_locales.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// Gestion de sucursales de MarFuego en el sur de chile
@Entity
@Data
public class Local {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del local es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "La ubicacion del local debe ser una de las opciones permitidas: CENTRO_PUERTO, PELLUCO, ANCUD, CASTRO")
    @Enumerated(EnumType.STRING) // Para guardar la ubicacion enum en la base de datos
    private Ubicacion ubicacion; // Sucursales oficiales

    @NotBlank(message = "La direccion es obligatoria")
    private String direccion;

    @NotBlank(message = "Debe haber un email asociado al local")
    private String emailAsociado;

    private String telefono;

    @Min(value = 0, message = "La capacidad de las mesas no puede ser negativa")
    private Integer capacidadMesas; //aforo disponible para reservas

    private boolean puedeDelivery; // Activa despacho en radio de 8km del local
    private boolean puedeCatering; // servicio de catering para eventos corporativos y matrimonios

    private boolean estaAbierto; // estado actual de atencion

    private boolean sincronizadoDisponible; // Sincronización cocina-caja para evitar falta de stock

}
