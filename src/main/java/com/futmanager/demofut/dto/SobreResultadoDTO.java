package com.futmanager.demofut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SobreResultadoDTO {
    private String tipoSobre;
    private Integer coste;
    private Integer monedasRestantes;
    private List<CartaFUTDTO> cartas = new ArrayList<>();
}
