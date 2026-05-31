package com.futmanager.demofut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantillaCartaDTO {
    private Long id;
    private Long plantillaId;
    private CartaFUTDTO cartaFUT;
    private String posicionEnPlantilla;
    private Boolean titular;
}
