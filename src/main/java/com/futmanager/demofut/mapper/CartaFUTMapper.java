package com.futmanager.demofut.mapper;

import com.futmanager.demofut.dto.CartaFUTDTO;
import com.futmanager.demofut.entity.CartaFUT;

public class CartaFUTMapper {

    private CartaFUTMapper() {
    }

    public static CartaFUTDTO entityToDto(CartaFUT carta) {
        if (carta == null) {
            return null;
        }

        Long jugadorId = carta.getJugador() == null ? null : carta.getJugador().getId();
        String jugadorNombre = carta.getJugador() == null ? null : carta.getJugador().getNombre();
        String jugadorPosicion = carta.getJugador() == null ? null : carta.getJugador().getPosicion();
        String nombreJugador = carta.getNombreJugador() != null ? carta.getNombreJugador() : jugadorNombre;
        var equipo = carta.getJugador() == null ? null : carta.getJugador().getEquipo();
        var liga = equipo == null ? null : equipo.getLigaDatos();

        CartaFUTDTO dto = new CartaFUTDTO();
        dto.setId(carta.getId());
        dto.setTipoCarta(carta.getTipoCarta());
        dto.setNombreJugador(nombreJugador);
        dto.setRareza(carta.getRareza());
        dto.setMedia(carta.getMedia());
        dto.setRitmo(carta.getRitmo());
        dto.setTiro(carta.getTiro());
        dto.setPase(carta.getPase());
        dto.setRegate(carta.getRegate());
        dto.setDefensa(carta.getDefensa());
        dto.setFisico(carta.getFisico());
        dto.setImagenJugador(carta.getImagenJugador());
        dto.setImagenEscudo(carta.getImagenEscudo());
        dto.setImagenBandera(carta.getImagenBandera());
        dto.setColorCarta(carta.getColorCarta());
        dto.setLiga(carta.getLiga());
        dto.setLigaId(liga == null ? null : liga.getId());
        dto.setNombreLiga(liga == null ? carta.getLiga() : liga.getNombre());
        dto.setPaisLiga(liga == null ? null : liga.getPais());
        dto.setClub(carta.getClub());
        dto.setEquipoId(equipo == null ? null : equipo.getId());
        dto.setNombreEquipo(equipo == null ? carta.getClub() : equipo.getNombre());
        dto.setNacionalidad(carta.getNacionalidad());
        dto.setNacionalidadJugador(carta.getJugador() == null ? carta.getNacionalidad() : carta.getJugador().getNacionalidad());
        dto.setPosicion(carta.getPosicion());
        dto.setPosicionCarta(carta.getPosicionCarta() == null ? carta.getPosicion() : carta.getPosicionCarta());
        dto.setPrecioMonedas(carta.getPrecioMonedas());
        dto.setEnClub(carta.getEnClub());
        dto.setTransferible(carta.getTransferible());
        dto.setEstiloCarta(carta.getEstiloCarta());
        dto.setJugadorId(jugadorId);
        dto.setJugadorNombre(jugadorNombre);
        dto.setJugadorPosicion(jugadorPosicion);
        dto.setEscudoEquipo(equipo == null ? carta.getImagenEscudo() : valor(equipo.getEscudoUrl(), equipo.getEscudo()));
        dto.setLogoLiga(liga == null ? null : valor(liga.getLogoUrl(), liga.getLogo()));
        return dto;
    }

    public static CartaFUT dtoToEntity(CartaFUTDTO dto) {
        if (dto == null) {
            return null;
        }

        CartaFUT carta = new CartaFUT();
        carta.setId(dto.getId());
        carta.setTipoCarta(dto.getTipoCarta());
        carta.setNombreJugador(dto.getNombreJugador());
        carta.setRareza(dto.getRareza());
        carta.setMedia(dto.getMedia());
        carta.setRitmo(dto.getRitmo());
        carta.setTiro(dto.getTiro());
        carta.setPase(dto.getPase());
        carta.setRegate(dto.getRegate());
        carta.setDefensa(dto.getDefensa());
        carta.setFisico(dto.getFisico());
        carta.setImagenJugador(dto.getImagenJugador());
        carta.setImagenEscudo(dto.getImagenEscudo());
        carta.setImagenBandera(dto.getImagenBandera());
        carta.setColorCarta(dto.getColorCarta());
        carta.setLiga(dto.getLiga());
        carta.setClub(dto.getClub());
        carta.setNacionalidad(dto.getNacionalidad());
        carta.setPosicion(valor(dto.getPosicion(), dto.getPosicionCarta()));
        carta.setPosicionCarta(valor(dto.getPosicionCarta(), dto.getPosicion()));
        carta.setPrecioMonedas(dto.getPrecioMonedas());
        carta.setEnClub(dto.getEnClub() == null ? true : dto.getEnClub());
        carta.setTransferible(dto.getTransferible() == null ? true : dto.getTransferible());
        carta.setEstiloCarta(dto.getEstiloCarta());
        return carta;
    }

    private static String valor(String principal, String alternativa) {
        return principal == null || principal.isBlank() ? alternativa : principal;
    }
}
