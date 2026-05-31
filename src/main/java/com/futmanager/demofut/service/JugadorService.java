package com.futmanager.demofut.service;

import com.futmanager.demofut.dto.JugadorDTO;
import com.futmanager.demofut.entity.Equipo;
import com.futmanager.demofut.entity.Jugador;
import com.futmanager.demofut.mapper.JugadorMapper;
import com.futmanager.demofut.repository.EquipoRepository;
import com.futmanager.demofut.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    public List<Jugador> findAll() {
        return jugadorRepository.findAll();
    }

    public Optional<Jugador> findById(Long id) {
        return jugadorRepository.findById(id);
    }

    public Jugador save(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    public void deleteById(Long id) {
        jugadorRepository.deleteById(id);
    }

    public List<Jugador> findByPosicion(String posicion) {
        return jugadorRepository.findByPosicion(posicion);
    }

    public List<Jugador> findByMediaMinima(Integer media) {
        return jugadorRepository.findByMediaGreaterThanEqual(media);
    }

    public List<Jugador> findByEquipo(Long equipoId) {
        return jugadorRepository.findByEquipoId(equipoId);
    }

    public List<Jugador> findByLiga(Long ligaId) {
        return jugadorRepository.findByEquipoLigaDatosId(ligaId);
    }

    public List<JugadorDTO> findAllDTO(String posicion, Integer mediaMinima, Long equipoId) {
        List<Jugador> jugadores;

        if (posicion != null) {
            jugadores = findByPosicion(posicion);
        } else if (mediaMinima != null) {
            jugadores = findByMediaMinima(mediaMinima);
        } else if (equipoId != null) {
            jugadores = findByEquipo(equipoId);
        } else {
            jugadores = findAll();
        }

        return jugadores.stream()
                .map(JugadorMapper::entityToDto)
                .toList();
    }

    public List<JugadorDTO> findDTOByEquipo(Long equipoId) {
        return findByEquipo(equipoId).stream().map(JugadorMapper::entityToDto).toList();
    }

    public List<JugadorDTO> findDTOByLiga(Long ligaId) {
        return findByLiga(ligaId).stream().map(JugadorMapper::entityToDto).toList();
    }

    public Page<JugadorDTO> findAllDTOPage(String liga, String equipo, String nacionalidad,
                                           String posicion, Integer mediaMinima, String buscar,
                                           Pageable pageable) {
        return jugadorRepository.filtrar(vacioANull(liga), vacioANull(equipo), vacioANull(nacionalidad),
                        vacioANull(posicion), mediaMinima, vacioANull(buscar), pageable)
                .map(JugadorMapper::entityToDto);
    }

    public Optional<JugadorDTO> findDTOById(Long id) {
        return jugadorRepository.findById(id).map(JugadorMapper::entityToDto);
    }

    public JugadorDTO createDTO(JugadorDTO dto) {
        Jugador jugador = JugadorMapper.dtoToEntity(dto);
        asignarEquipo(jugador, dto.getEquipoId());
        return JugadorMapper.entityToDto(jugadorRepository.save(jugador));
    }

    public Optional<JugadorDTO> updateDTO(Long id, JugadorDTO dto) {
        return jugadorRepository.findById(id).map(jugador -> {
            jugador.setNombre(dto.getNombre());
            jugador.setApellido(dto.getApellido());
            jugador.setNombreCompleto(dto.getNombreCompleto());
            jugador.setPosicion(dto.getPosicion());
            jugador.setPosicionPrincipal(dto.getPosicionPrincipal());
            jugador.setPosicionSecundaria(dto.getPosicionSecundaria());
            jugador.setNacionalidad(dto.getNacionalidad());
            jugador.setEdad(dto.getEdad());
            jugador.setMedia(dto.getMedia());
            jugador.setRitmo(dto.getRitmo());
            jugador.setTiro(dto.getTiro());
            jugador.setPase(dto.getPase());
            jugador.setRegate(dto.getRegate());
            jugador.setDefensa(dto.getDefensa());
            jugador.setFisico(dto.getFisico());
            jugador.setLiga(dto.getLiga());
            jugador.setFoto(dto.getFoto());
            jugador.setFotoUrl(dto.getFotoUrl());
            jugador.setAltura(dto.getAltura());
            jugador.setPeso(dto.getPeso());
            asignarEquipo(jugador, dto.getEquipoId());
            return JugadorMapper.entityToDto(jugadorRepository.save(jugador));
        });
    }

    private void asignarEquipo(Jugador jugador, Long equipoId) {
        if (equipoId == null) {
            throw new IllegalArgumentException("El equipo no puede ser nulo");
        }

        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new IllegalArgumentException("El equipo especificado no existe"));
        jugador.setEquipo(equipo);
        if (jugador.getLiga() == null || jugador.getLiga().isBlank()) {
            jugador.setLiga(equipo.getLiga());
        }
        if (jugador.getPosicionPrincipal() == null || jugador.getPosicionPrincipal().isBlank()) {
            jugador.setPosicionPrincipal(jugador.getPosicion());
        }
        if (jugador.getNombreCompleto() == null || jugador.getNombreCompleto().isBlank()) {
            jugador.setNombreCompleto(jugador.getNombre());
        }
    }

    private String vacioANull(String valor) {
        return valor == null || valor.isBlank() ? null : valor;
    }
}
