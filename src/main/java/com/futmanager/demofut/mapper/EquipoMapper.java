package com.futmanager.demofut.mapper;

import com.futmanager.demofut.dto.EquipoDTO;
import com.futmanager.demofut.entity.Equipo;

public class EquipoMapper {

    private EquipoMapper() {
    }

    public static EquipoDTO entityToDto(Equipo equipo) {
        if (equipo == null) {
            return null;
        }

        Integer numeroJugadores = equipo.getJugadores() == null ? 0 : equipo.getJugadores().size();
        return new EquipoDTO(
                equipo.getId(),
                equipo.getNombre(),
                equipo.getLiga(),
                equipo.getLigaDatos() == null ? equipo.getLiga() : equipo.getLigaDatos().getNombre(),
                equipo.getPais(),
                equipo.getCiudad(),
                equipo.getEscudo(),
                valor(equipo.getEscudoUrl(), equipo.getEscudo()),
                equipo.getEstadio(),
                equipo.getLigaDatos() == null ? null : equipo.getLigaDatos().getId(),
                numeroJugadores
        );
    }

    public static Equipo dtoToEntity(EquipoDTO dto) {
        if (dto == null) {
            return null;
        }

        Equipo equipo = new Equipo();
        equipo.setId(dto.getId());
        equipo.setNombre(dto.getNombre());
        equipo.setLiga(valor(dto.getLiga(), dto.getNombreLiga()));
        equipo.setPais(dto.getPais());
        equipo.setCiudad(dto.getCiudad());
        equipo.setEscudo(dto.getEscudo());
        equipo.setEscudoUrl(valor(dto.getEscudoUrl(), dto.getEscudo()));
        equipo.setEstadio(dto.getEstadio());
        return equipo;
    }

    private static String valor(String principal, String alternativa) {
        return principal == null || principal.isBlank() ? alternativa : principal;
    }
}
