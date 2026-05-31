package com.futmanager.demofut.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigaDTO {

    private Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;

    private String pais;
    private String logoUrl;
    private Integer nivel;
    private String temporada;
    private Integer numeroEquipos;
}
