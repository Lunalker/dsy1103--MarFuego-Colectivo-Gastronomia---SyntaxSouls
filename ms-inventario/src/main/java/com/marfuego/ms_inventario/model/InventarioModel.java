package com.marfuego.ms_inventario.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity

@Schema(
        name = "Inventario",
        description = "Representa los ingredientes del inventario"
)
public class InventarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Nombre del ingrediente",
            example = "Mantequilla"
    )
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Schema(
            description = "Stock actual del ingrediente",
            example = "50"
    )
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    private int stock;
    public InventarioModel(Long id, String nombre, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
