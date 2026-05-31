package com.futmanager.demofut.service;

import com.futmanager.demofut.entity.CartaFUT;
import com.futmanager.demofut.entity.EvolucionCarta;
import com.futmanager.demofut.repository.CartaFUTRepository;
import com.futmanager.demofut.repository.EvolucionCartaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvolucionCartaService {

    @Autowired
    private EvolucionCartaRepository evolucionRepository;

    @Autowired
    private CartaFUTRepository cartaFUTRepository;

    public List<EvolucionCarta> findAll() {
        return evolucionRepository.findAll();
    }

    public EvolucionCarta evolucionar(Long cartaId) {
        CartaFUT carta = cartaFUTRepository.findById(cartaId)
                .orElseThrow(() -> new IllegalArgumentException("La carta no existe"));

        carta.setMedia(limitar(carta.getMedia() + 2));
        carta.setRitmo(limitar(valor(carta.getRitmo()) + 2));
        carta.setTiro(limitar(valor(carta.getTiro()) + 2));
        carta.setPase(limitar(valor(carta.getPase()) + 2));
        carta.setRegate(limitar(valor(carta.getRegate()) + 1));
        carta.setDefensa(limitar(valor(carta.getDefensa()) + 1));
        carta.setFisico(limitar(valor(carta.getFisico()) + 1));
        if (carta.getMedia() >= 90) {
            carta.setRareza("Especial");
            carta.setTipoCarta("Especial");
            carta.setColorCarta("#60a5fa");
        }
        cartaFUTRepository.save(carta);

        EvolucionCarta evolucion = new EvolucionCarta();
        evolucion.setCartaFUT(carta);
        evolucion.setNombreEvolucion("Mejora de rendimiento");
        evolucion.setDescripcion("Sube media y stats principales de forma sencilla.");
        evolucion.setCompletada(true);
        evolucion.setMejoraMedia(2);
        evolucion.setMejoraRitmo(2);
        evolucion.setMejoraTiro(2);
        evolucion.setMejoraPase(2);
        evolucion.setMejoraRegate(1);
        evolucion.setMejoraDefensa(1);
        evolucion.setMejoraFisico(1);
        return evolucionRepository.save(evolucion);
    }

    private int valor(Integer numero) {
        return numero == null ? 55 : numero;
    }

    private int limitar(Integer numero) {
        return Math.max(1, Math.min(99, valor(numero)));
    }
}
