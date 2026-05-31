package com.futmanager.demofut.service;

import com.futmanager.demofut.dto.CartaFUTDTO;
import com.futmanager.demofut.dto.MonederoDTO;
import com.futmanager.demofut.entity.CartaFUT;
import com.futmanager.demofut.mapper.CartaFUTMapper;
import com.futmanager.demofut.repository.CartaFUTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MercadoService {

    @Autowired
    private CartaFUTRepository cartaFUTRepository;

    @Autowired
    private MonederoService monederoService;

    public Page<CartaFUTDTO> buscarCartas(String nombre, String posicion, Integer mediaMin,
                                          String liga, String club, String rareza, Pageable pageable) {
        return cartaFUTRepository.filtrar(vacioANull(liga), vacioANull(club), null,
                        vacioANull(posicion), vacioANull(rareza), mediaMin, vacioANull(nombre), pageable)
                .map(CartaFUTMapper::entityToDto);
    }

    public MonederoDTO comprar(Long cartaId) {
        CartaFUT carta = cartaFUTRepository.findById(cartaId)
                .orElseThrow(() -> new IllegalArgumentException("La carta no existe"));
        var club = monederoService.gastar(precio(carta));
        carta.setEnClub(true);
        cartaFUTRepository.save(carta);
        return new MonederoDTO(club.getMonedas());
    }

    public MonederoDTO vender(Long cartaId) {
        CartaFUT carta = cartaFUTRepository.findById(cartaId)
                .orElseThrow(() -> new IllegalArgumentException("La carta no existe"));
        carta.setEnClub(false);
        cartaFUTRepository.save(carta);
        return new MonederoDTO(monederoService.ganar(precio(carta) / 2).getMonedas());
    }

    private int precio(CartaFUT carta) {
        return carta.getPrecioMonedas() == null ? 0 : carta.getPrecioMonedas();
    }

    private String vacioANull(String valor) {
        return valor == null || valor.isBlank() ? null : valor;
    }
}
