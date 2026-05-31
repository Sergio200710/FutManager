package com.futmanager.demofut.service;

import com.futmanager.demofut.dto.CartaFUTDTO;
import com.futmanager.demofut.dto.SobreResultadoDTO;
import com.futmanager.demofut.entity.CartaFUT;
import com.futmanager.demofut.entity.ClubUsuario;
import com.futmanager.demofut.entity.Sobre;
import com.futmanager.demofut.mapper.CartaFUTMapper;
import com.futmanager.demofut.repository.CartaFUTRepository;
import com.futmanager.demofut.repository.SobreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Service
public class SobreService {

    private final Random random = new Random();

    @Autowired
    private CartaFUTRepository cartaFUTRepository;

    @Autowired
    private MonederoService monederoService;

    @Autowired
    private SobreRepository sobreRepository;

    public List<Sobre> findAll() {
        crearSobresBase();
        return sobreRepository.findAll();
    }

    public SobreResultadoDTO abrir(String tipoSobre) {
        String tipo = tipoSobre == null ? "oro" : tipoSobre.toLowerCase();
        int coste = coste(tipo);
        ClubUsuario usuario = monederoService.gastar(coste);

        List<CartaFUT> cartas = filtrarCartasPorSobre(cartaFUTRepository.findAll(), tipo);
        if (cartas.isEmpty()) {
            throw new IllegalArgumentException("No hay cartas disponibles para abrir sobres");
        }

        List<CartaFUT> cartasObtenidas = cartas.stream()
                .sorted(Comparator.comparing(carta -> random.nextInt(100) + bonusPorSobre(carta, tipo)))
                .skip(Math.max(0, cartas.size() - 5))
                .peek(carta -> carta.setEnClub(true))
                .toList();
        cartaFUTRepository.saveAll(cartasObtenidas);

        List<CartaFUTDTO> obtenidas = cartasObtenidas.stream()
                .map(CartaFUTMapper::entityToDto)
                .toList();

        return new SobreResultadoDTO(tipoSobre, coste, usuario.getMonedas(), obtenidas);
    }

    private void crearSobresBase() {
        if (sobreRepository.count() > 0) return;
        sobreRepository.save(new Sobre(null, "Sobre bronce", "Bronce", 750, 5, "bronce", "Cartas de media baja para empezar."));
        sobreRepository.save(new Sobre(null, "Sobre plata", "Plata", 2500, 5, "plata", "Cartas plata con opciones utiles."));
        sobreRepository.save(new Sobre(null, "Sobre oro", "Oro", 7500, 5, "oro", "Cartas oro comunes."));
        sobreRepository.save(new Sobre(null, "Sobre oro premium", "Oro Premium", 15000, 5, "oro-premium", "Mayor opcion de cartas 85+."));
        sobreRepository.save(new Sobre(null, "Sobre especial", "Especial", 25000, 5, "especial", "Alta opcion de cartas especiales."));
        sobreRepository.save(new Sobre(null, "Sobre ultimate", "Ultimate", 50000, 8, "ultimate", "Mas cartas y mejores probabilidades."));
    }

    private int coste(String tipo) {
        if (tipo.contains("ultimate")) return 50000;
        if (tipo.contains("especial")) return 25000;
        if (tipo.contains("oro")) return 7500;
        if (tipo.contains("plata")) return 2500;
        return 750;
    }

    private int bonusPorSobre(CartaFUT carta, String tipo) {
        int media = carta.getMedia() == null ? 55 : carta.getMedia();
        if (tipo.contains("ultimate")) return media * 3;
        if (tipo.contains("especial")) return media * 2;
        if (tipo.contains("oro")) return media >= 75 ? media : media / 2;
        if (tipo.contains("plata")) return media >= 65 && media <= 82 ? media : media / 3;
        return media <= 72 ? media : media / 4;
    }

    private List<CartaFUT> filtrarCartasPorSobre(List<CartaFUT> cartas, String tipo) {
        List<CartaFUT> filtradas = cartas.stream()
                .filter(carta -> encajaEnSobre(carta.getMedia() == null ? 55 : carta.getMedia(), tipo, carta.getRareza()))
                .toList();
        return filtradas.isEmpty() ? cartas : filtradas;
    }

    private boolean encajaEnSobre(int media, String tipo, String rareza) {
        if (tipo.contains("ultimate")) return media >= 75;
        if (tipo.contains("especial")) return media >= 80 || (rareza != null && rareza.contains("Especial"));
        if (tipo.contains("premium")) return media >= 75;
        if (tipo.contains("oro")) return media >= 75 && media <= 84;
        if (tipo.contains("plata")) return media >= 65 && media <= 74;
        return media >= 55 && media <= 64;
    }
}
