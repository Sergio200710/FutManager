package com.futmanager.demofut.mapper;

import com.futmanager.demofut.dto.LigaDTO;
import com.futmanager.demofut.entity.Liga;

public class LigaMapper {

    private LigaMapper() {
    }

    public static LigaDTO entityToDto(Liga liga) {
        if (liga == null) return null;
        return new LigaDTO(
                liga.getId(),
                liga.getNombre(),
                liga.getPais(),
                valor(liga.getLogoUrl(), liga.getLogo()),
                liga.getNivel(),
                liga.getTemporada(),
                liga.getEquipos() == null ? 0 : liga.getEquipos().size()
        );
    }

    public static Liga dtoToEntity(LigaDTO dto) {
        Liga liga = new Liga();
        liga.setId(dto.getId());
        liga.setNombre(dto.getNombre());
        liga.setPais(dto.getPais());
        liga.setLogoUrl(dto.getLogoUrl());
        liga.setLogo(dto.getLogoUrl());
        liga.setNivel(dto.getNivel());
        liga.setTemporada(dto.getTemporada());
        return liga;
    }

    private static String valor(String principal, String alternativa) {
        return principal == null || principal.isBlank() ? alternativa : principal;
    }
}
