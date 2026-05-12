package com.marfuego.ms_locales.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

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

    // R3: guardamos la hora cuando empieza la limpieza, asi sabemos cuando pasa a LIBRE
    @Column(name = "inicio_limpieza")
    private LocalDateTime inicioLimpieza;

    @ManyToOne
    @JoinColumn(name = "local_id", nullable = false)
    private LocalLocales local;
}
