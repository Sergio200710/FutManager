package com.futmanager.demofut.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipoDTO {

    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    private String liga;
    private String nombreLiga;
    private String pais;
    private String ciudad;
    private String escudo;
    private String escudoUrl;
    private String estadio;
    private Long ligaId;
    private Integer numeroJugadores;
}
