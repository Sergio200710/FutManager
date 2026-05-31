package com.futmanager.demofut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubUsuarioDTO {
    private Long id;
    private String nombreClub;
    private Integer monedas;
    private Integer nivel;
    private Integer experiencia;
    private Integer partidosJugados;
    private Integer victorias;
    private Integer empates;
    private Integer derrotas;
}
