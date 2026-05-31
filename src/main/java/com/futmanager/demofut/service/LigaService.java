package com.futmanager.demofut.service;

import com.futmanager.demofut.dto.LigaDTO;
import com.futmanager.demofut.entity.Liga;
import com.futmanager.demofut.mapper.LigaMapper;
import com.futmanager.demofut.repository.LigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LigaService {

    @Autowired
    private LigaRepository ligaRepository;

    public List<LigaDTO> findAllDTO() {
        return ligaRepository.findAll().stream().map(LigaMapper::entityToDto).toList();
    }

    public Optional<LigaDTO> findDTOById(Long id) {
        return ligaRepository.findById(id).map(LigaMapper::entityToDto);
    }

    public Optional<Liga> findById(Long id) {
        return ligaRepository.findById(id);
    }

    public LigaDTO createDTO(LigaDTO dto) {
        return LigaMapper.entityToDto(ligaRepository.save(LigaMapper.dtoToEntity(dto)));
    }

    public Optional<LigaDTO> updateDTO(Long id, LigaDTO dto) {
        return ligaRepository.findById(id).map(liga -> {
            liga.setNombre(dto.getNombre());
            liga.setPais(dto.getPais());
            liga.setLogo(dto.getLogoUrl());
            liga.setLogoUrl(dto.getLogoUrl());
            liga.setNivel(dto.getNivel());
            liga.setTemporada(dto.getTemporada());
            return LigaMapper.entityToDto(ligaRepository.save(liga));
        });
    }

    public void deleteById(Long id) {
        ligaRepository.deleteById(id);
    }
}
