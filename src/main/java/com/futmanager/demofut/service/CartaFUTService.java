package com.futmanager.demofut.service;

import com.futmanager.demofut.dto.CartaFUTDTO;
import com.futmanager.demofut.entity.CartaFUT;
import com.futmanager.demofut.entity.Jugador;
import com.futmanager.demofut.mapper.CartaFUTMapper;
import com.futmanager.demofut.repository.CartaFUTRepository;
import com.futmanager.demofut.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartaFUTService {

    @Autowired
    private CartaFUTRepository cartaFUTRepository;

    @Autowired
    private JugadorRepository jugadorRepository;

    public List<CartaFUT> findAll() {
        return cartaFUTRepository.findAll();
    }

    public Optional<CartaFUT> findById(Long id) {
        return cartaFUTRepository.findById(id);
    }

    public CartaFUT save(CartaFUT cartaFUT) {
        return cartaFUTRepository.save(cartaFUT);
    }

    public void deleteById(Long id) {
        cartaFUTRepository.deleteById(id);
    }

    public List<CartaFUTDTO> findAllDTO() {
        return cartaFUTRepository.findAll().stream()
                .map(CartaFUTMapper::entityToDto)
                .toList();
    }

    public Page<CartaFUTDTO> findAllDTOPage(String liga, String club, String nacionalidad,
                                            String posicion, String rareza, Integer mediaMin,
                                            String buscar, Pageable pageable) {
        return cartaFUTRepository.filtrar(vacioANull(liga), vacioANull(club), vacioANull(nacionalidad),
                        vacioANull(posicion), vacioANull(rareza), mediaMin, vacioANull(buscar), pageable)
                .map(CartaFUTMapper::entityToDto);
    }

    public Optional<CartaFUTDTO> findDTOById(Long id) {
        return cartaFUTRepository.findById(id).map(CartaFUTMapper::entityToDto);
    }

    public CartaFUTDTO createDTO(CartaFUTDTO dto) {
        CartaFUT carta = CartaFUTMapper.dtoToEntity(dto);
        asignarJugador(carta, dto.getJugadorId());
        return CartaFUTMapper.entityToDto(cartaFUTRepository.save(carta));
    }

    public Optional<CartaFUTDTO> updateDTO(Long id, CartaFUTDTO dto) {
        return cartaFUTRepository.findById(id).map(carta -> {
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
            carta.setPosicion(dto.getPosicion());
            carta.setPrecioMonedas(dto.getPrecioMonedas());
            carta.setEnClub(dto.getEnClub() == null ? carta.getEnClub() : dto.getEnClub());
            carta.setTransferible(dto.getTransferible() == null ? carta.getTransferible() : dto.getTransferible());
            carta.setEstiloCarta(dto.getEstiloCarta());
            asignarJugador(carta, dto.getJugadorId());
            return CartaFUTMapper.entityToDto(cartaFUTRepository.save(carta));
        });
    }

    public List<CartaFUTDTO> guardarCartasImportadas(List<CartaFUTDTO> cartas) {
        return cartas.stream()
                .map(this::createDTO)
                .toList();
    }

    public Page<CartaFUTDTO> buscarDTO(String nombre, String club, String liga, String nacionalidad,
                                       String posicion, String rareza, Integer mediaMin, Integer mediaMax,
                                       Integer precioMin, Integer precioMax, Pageable pageable) {
        return cartaFUTRepository.buscar(vacioANull(nombre), vacioANull(club), vacioANull(liga),
                        vacioANull(nacionalidad), vacioANull(posicion), vacioANull(rareza),
                        mediaMin, mediaMax, precioMin, precioMax, pageable)
                .map(CartaFUTMapper::entityToDto);
    }

    public Page<CartaFUTDTO> buscarNormalizadoDTO(String nombreJugador, Long ligaId, Long equipoId,
                                                  String nacionalidad, String posicion, String rareza,
                                                  String tipoCarta, Integer mediaMin, Integer mediaMax,
                                                  Integer precioMin, Integer precioMax, Pageable pageable) {
        return cartaFUTRepository.buscarNormalizado(vacioANull(nombreJugador), ligaId, equipoId,
                        vacioANull(nacionalidad), vacioANull(posicion), vacioANull(rareza),
                        vacioANull(tipoCarta), mediaMin, mediaMax, precioMin, precioMax, pageable)
                .map(CartaFUTMapper::entityToDto);
    }

    public List<CartaFUTDTO> findDTOByJugador(Long jugadorId) {
        return cartaFUTRepository.findByJugadorId(jugadorId).stream()
                .map(CartaFUTMapper::entityToDto)
                .toList();
    }

    public List<CartaFUTDTO> findMisCartas() {
        return cartaFUTRepository.findByEnClubTrue().stream()
                .map(CartaFUTMapper::entityToDto)
                .toList();
    }

    public Optional<CartaFUTDTO> cambiarTransferible(Long cartaId, Boolean transferible) {
        return cartaFUTRepository.findById(cartaId).map(carta -> {
            carta.setTransferible(transferible == null ? true : transferible);
            return CartaFUTMapper.entityToDto(cartaFUTRepository.save(carta));
        });
    }

    private void asignarJugador(CartaFUT carta, Long jugadorId) {
        if (jugadorId == null) {
            carta.setJugador(null);
            return;
        }

        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new IllegalArgumentException("El jugador especificado no existe"));
        carta.setJugador(jugador);
    }

    private String vacioANull(String valor) {
        return valor == null || valor.isBlank() ? null : valor;
    }
}
