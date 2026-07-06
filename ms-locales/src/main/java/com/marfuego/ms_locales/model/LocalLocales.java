package com.marfuego.ms_locales.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entidad que representa un local de la cadena. Se guarda en la tabla local y
 * agrupa a sus mesas.
 */
@Entity
@Table(name = "local")
@Data
public class LocalLocales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Ubicacion ubicacion;
}
