package com.futmanager.demofut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MercadoCartaDTO {
    private Long id;
    private CartaFUTDTO cartaFUT;
    private Integer precio;
    private Boolean vendida;
}
