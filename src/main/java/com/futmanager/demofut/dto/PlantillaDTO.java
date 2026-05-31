package com.futmanager.demofut.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantillaDTO {

    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    private String formacion;
    private Integer mediaTotal;
    private Integer quimica;
    private Integer valoracionOfensiva;
    private Integer valoracionDefensiva;
    private Integer ataque;
    private Integer defensa;
    private Integer valoracionGeneral;
    private String explicacionQuimica;
    private List<CartaFUTDTO> cartas = new ArrayList<>();
}
