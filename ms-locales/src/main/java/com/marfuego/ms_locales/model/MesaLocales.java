package com.marfuego.ms_locales.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Entidad que representa una mesa de un local. Se guarda en la tabla mesa. Los
 * campos estado e inicioLimpieza son los que hacen funcionar la regla R3.
 */
@Entity
@Table(name = "mesa")
@Data
public class MesaLocales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_mesa", nullable = false)
    private Integer numeroMesa;

    @Column(nullable = false)
    private Integer capacidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Estado estado;

    // R3: guardamos la hora cuando empieza la limpieza, así sabemos cuándo pasa a LIBRE
    @Column(name = "inicio_limpieza")
    private LocalDateTime inicioLimpieza;

    @ManyToOne
    @JoinColumn(name = "local_id", nullable = false)
    private LocalLocales local;
}
