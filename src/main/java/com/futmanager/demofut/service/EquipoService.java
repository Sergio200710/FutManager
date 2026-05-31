package com.futmanager.demofut.service;

import com.futmanager.demofut.dto.EquipoDTO;
import com.futmanager.demofut.entity.Equipo;
import com.futmanager.demofut.mapper.EquipoMapper;
import com.futmanager.demofut.repository.EquipoRepository;
import com.futmanager.demofut.repository.LigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private LigaRepository ligaRepository;

    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }

    public Optional<Equipo> findById(Long id) {
        return equipoRepository.findById(id);
    }

    public Equipo save(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    public void deleteById(Long id) {
        equipoRepository.deleteById(id);
    }

    public List<EquipoDTO> findAllDTO() {
        return equipoRepository.findAll().stream()
                .map(EquipoMapper::entityToDto)
                .toList();
    }

    public Optional<EquipoDTO> findDTOById(Long id) {
        return equipoRepository.findById(id).map(EquipoMapper::entityToDto);
    }

    public EquipoDTO createDTO(EquipoDTO dto) {
        Equipo equipo = EquipoMapper.dtoToEntity(dto);
        asignarLiga(equipo, dto.getLigaId());
        return EquipoMapper.entityToDto(equipoRepository.save(equipo));
    }

    public Optional<EquipoDTO> updateDTO(Long id, EquipoDTO dto) {
        return equipoRepository.findById(id).map(equipo -> {
            equipo.setNombre(dto.getNombre());
            equipo.setLiga(dto.getLiga());
            equipo.setPais(dto.getPais());
            equipo.setCiudad(dto.getCiudad());
            equipo.setEscudo(dto.getEscudo());
            equipo.setEscudoUrl(dto.getEscudoUrl());
            equipo.setEstadio(dto.getEstadio());
            asignarLiga(equipo, dto.getLigaId());
            return EquipoMapper.entityToDto(equipoRepository.save(equipo));
        });
    }

    public List<EquipoDTO> findByLiga(Long ligaId) {
        return equipoRepository.findByLigaDatosId(ligaId).stream()
                .map(EquipoMapper::entityToDto)
                .toList();
    }

    private void asignarLiga(Equipo equipo, Long ligaId) {
        if (ligaId == null) return;
        var liga = ligaRepository.findById(ligaId)
                .orElseThrow(() -> new IllegalArgumentException("La liga especificada no existe"));
        equipo.setLigaDatos(liga);
        equipo.setLiga(liga.getNombre());
    }
}
