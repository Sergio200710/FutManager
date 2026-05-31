package com.futmanager.demofut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvolucionCartaDTO {
    private Long id;
    private CartaFUTDTO cartaFUT;
    private String nombreEvolucion;
    private String descripcion;
    private Boolean completada;
    private Integer mejoraMedia;
    private Integer mejoraRitmo;
    private Integer mejoraTiro;
    private Integer mejoraPase;
    private Integer mejoraRegate;
    private Integer mejoraDefensa;
    private Integer mejoraFisico;
}
