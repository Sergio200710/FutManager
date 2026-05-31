package com.futmanager.demofut.mapper;

import com.futmanager.demofut.dto.JugadorDTO;
import com.futmanager.demofut.entity.Jugador;

public class JugadorMapper {

    private JugadorMapper() {
    }

    public static JugadorDTO entityToDto(Jugador jugador) {
        if (jugador == null) {
            return null;
        }

        Long equipoId = jugador.getEquipo() == null ? null : jugador.getEquipo().getId();
        String equipoNombre = jugador.getEquipo() == null ? null : jugador.getEquipo().getNombre();
        String nombreLiga = jugador.getEquipo() == null || jugador.getEquipo().getLigaDatos() == null
                ? jugador.getLiga() : jugador.getEquipo().getLigaDatos().getNombre();

        return new JugadorDTO(
                jugador.getId(),
                jugador.getNombre(),
                jugador.getApellido(),
                valor(jugador.getNombreCompleto(), jugador.getNombre()),
                jugador.getPosicion(),
                valor(jugador.getPosicionPrincipal(), jugador.getPosicion()),
                jugador.getPosicionSecundaria(),
                jugador.getNacionalidad(),
                jugador.getEdad(),
                jugador.getMedia(),
                jugador.getRitmo(),
                jugador.getTiro(),
                jugador.getPase(),
                jugador.getRegate(),
                jugador.getDefensa(),
                jugador.getFisico(),
                jugador.getLiga(),
                nombreLiga,
                jugador.getFoto(),
                valor(jugador.getFotoUrl(), jugador.getFoto()),
                jugador.getAltura(),
                jugador.getPeso(),
                equipoId,
                equipoNombre,
                equipoNombre
        );
    }

    public static Jugador dtoToEntity(JugadorDTO dto) {
        if (dto == null) {
            return null;
        }

        Jugador jugador = new Jugador();
        jugador.setId(dto.getId());
        jugador.setNombre(dto.getNombre());
        jugador.setApellido(dto.getApellido());
        jugador.setNombreCompleto(valor(dto.getNombreCompleto(), dto.getNombre()));
        jugador.setPosicion(valor(dto.getPosicion(), dto.getPosicionPrincipal()));
        jugador.setPosicionPrincipal(valor(dto.getPosicionPrincipal(), dto.getPosicion()));
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
        jugador.setFotoUrl(valor(dto.getFotoUrl(), dto.getFoto()));
        jugador.setAltura(dto.getAltura());
        jugador.setPeso(dto.getPeso());
        return jugador;
    }

    private static String valor(String principal, String alternativa) {
        return principal == null || principal.isBlank() ? alternativa : principal;
    }
}
