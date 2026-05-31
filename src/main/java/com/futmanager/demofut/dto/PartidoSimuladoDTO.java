package com.futmanager.demofut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartidoSimuladoDTO {

    private Long id;
    private Long plantillaId;
    private String plantillaNombre;
    private String rival;
    private Integer golesUsuario;
    private Integer golesRival;
    private String ganador;
    private String resumen;
    private String mejoresJugadores;
    private String mvp;
    private Integer monedasGanadas;
    private Integer experienciaGanada;
    private String eventos;
    private LocalDateTime fecha;
}
