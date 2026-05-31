package com.futmanager.demofut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjetivoDTO {
    private Long id;
    private String descripcion;
    private String tipo;
    private Integer progresoActual;
    private Integer progresoNecesario;
    private Boolean completado;
    private Integer recompensaMonedas;
    private Integer recompensaExperiencia;
}
