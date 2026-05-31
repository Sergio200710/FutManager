package com.futmanager.demofut.mapper;

import com.futmanager.demofut.dto.CartaFUTDTO;
import com.futmanager.demofut.dto.PlantillaDTO;
import com.futmanager.demofut.entity.Plantilla;

import java.util.List;

public class PlantillaMapper {

    private PlantillaMapper() {
    }

    public static PlantillaDTO entityToDto(Plantilla plantilla) {
        if (plantilla == null) {
            return null;
        }

        List<CartaFUTDTO> cartas = plantilla.getCartas().stream()
                .map(plantillaCarta -> CartaFUTMapper.entityToDto(plantillaCarta.getCarta()))
                .toList();

        return new PlantillaDTO(
                plantilla.getId(),
                plantilla.getNombre(),
                plantilla.getFormacion(),
                plantilla.getMediaTotal(),
                plantilla.getQuimica(),
                plantilla.getValoracionOfensiva(),
                plantilla.getValoracionDefensiva(),
                plantilla.getValoracionOfensiva(),
                plantilla.getValoracionDefensiva(),
                plantilla.getValoracionGeneral(),
                null,
                cartas
        );
    }
}
