package com.marfuego.ms_locales.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "mesas")
@Data
public class MesaLocales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer numeroMesa;

    private String estado; // LIBRE, OCUPADA, LIMPIEZA [cite: 3]

    @ManyToOne
    @JoinColumn(name = "local_id")
    private LocalLocales local;
}