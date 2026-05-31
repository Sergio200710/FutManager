package com.futmanager.demofut.mapper;

import com.futmanager.demofut.dto.PartidoSimuladoDTO;
import com.futmanager.demofut.entity.PartidoSimulado;

public class PartidoSimuladoMapper {

    private PartidoSimuladoMapper() {
    }

    public static PartidoSimuladoDTO entityToDto(PartidoSimulado partido) {
        if (partido == null) {
            return null;
        }

        Long plantillaId = partido.getPlantilla() == null ? null : partido.getPlantilla().getId();
        String plantillaNombre = partido.getNombrePlantilla() != null ? partido.getNombrePlantilla()
                : partido.getPlantilla() == null ? null : partido.getPlantilla().getNombre();

        return new PartidoSimuladoDTO(
                partido.getId(),
                plantillaId,
                plantillaNombre,
                partido.getRival(),
                partido.getGolesUsuario(),
                partido.getGolesRival(),
                partido.getGanador(),
                partido.getResumen(),
                partido.getMejoresJugadores(),
                partido.getMvp(),
                partido.getMonedasGanadas(),
                partido.getExperienciaGanada(),
                partido.getEventos(),
                partido.getFecha()
        );
    }
}
