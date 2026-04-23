package com.Gastronomia.MarFuego.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class MesaGastronomia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;
    private String estado; // LIBRE, OCUPADA, LIMPIEZA (R3)
}
