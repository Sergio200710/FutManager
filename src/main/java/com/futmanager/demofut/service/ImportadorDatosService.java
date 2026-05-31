package com.futmanager.demofut.service;

import com.futmanager.demofut.entity.CartaFUT;
import com.futmanager.demofut.entity.Equipo;
import com.futmanager.demofut.entity.Jugador;
import com.futmanager.demofut.entity.Liga;
import com.futmanager.demofut.repository.CartaFUTRepository;
import com.futmanager.demofut.repository.EquipoRepository;
import com.futmanager.demofut.repository.JugadorRepository;
import com.futmanager.demofut.repository.LigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ImportadorDatosService {

    @Autowired private LigaRepository ligaRepository;
    @Autowired private EquipoRepository equipoRepository;
    @Autowired private JugadorRepository jugadorRepository;
    @Autowired private CartaFUTRepository cartaFUTRepository;
    @Autowired private CartaFUTGeneratorService cartaGeneratorService;
    @Autowired private FootballApiService footballApiService;

    public Map<String, Object> importarLigas() {
        return Map.of("mensaje", "Utiliza el DemoDataLoader o los endpoints reales.");
    }

    public Map<String, Object> importarEquipos() {
        return Map.of("mensaje", "Utiliza el DemoDataLoader o los endpoints reales.");
    }

    public Map<String, Object> importarJugadores() {
        return Map.of("mensaje", "Utiliza el DemoDataLoader o los endpoints reales.");
    }

    public Map<String, Object> importarCartas() {
        return Map.of("mensaje", "Utiliza el DemoDataLoader o los endpoints reales.");
    }

    public Map<String, Object> importarTodo() {
        return Map.of("mensaje", "Los datos demo ahora se cargan automáticamente al iniciar con DemoDataLoader.");
    }

    public Map<String, Object> importarEquiposReales() {
        List<Map<String, Object>> jugadores = footballApiService.importarJugadoresReales();
        for (Map<String, Object> jugadorReal : jugadores) {
            String nombreClub = String.valueOf(jugadorReal.get("club"));
            Equipo equipo = equipoRepository.findByNombreIgnoreCase(nombreClub).orElseGet(Equipo::new);
            equipo.setNombre(nombreClub);
            equipo.setLiga(String.valueOf(jugadorReal.get("liga")));
            equipo.setPais(paisPorLiga(equipo.getLiga()));
            equipo.setEscudo(String.valueOf(jugadorReal.get("imagenEscudo")));
            equipo.setEstadio("Estadio " + nombreClub);
            equipoRepository.save(equipo);
        }
        return resumen("equipos reales", equipoRepository.count());
    }

    public Map<String, Object> importarJugadoresReales() {
        importarEquiposReales();
        List<Map<String, Object>> jugadoresReales = footballApiService.importarJugadoresReales();
        int nuevos = 0;

        for (Map<String, Object> dato : jugadoresReales) {
            String nombre = String.valueOf(dato.get("nombre"));
            String club = String.valueOf(dato.get("club"));
            boolean existe = jugadorRepository.findAll().stream()
                    .anyMatch(j -> j.getNombre().equalsIgnoreCase(nombre)
                            && j.getEquipo() != null
                            && j.getEquipo().getNombre().equalsIgnoreCase(club));
            if (existe) continue;

            Equipo equipo = equipoRepository.findByNombreIgnoreCase(club)
                    .orElseThrow(() -> new IllegalArgumentException("No existe el equipo " + club));
            Jugador jugador = new Jugador();
            jugador.setNombre(nombre);
            jugador.setApellido("");
            jugador.setEquipo(equipo);
            jugador.setLiga(String.valueOf(dato.get("liga")));
            jugador.setNacionalidad(String.valueOf(dato.get("nacionalidad")));
            jugador.setPosicion(String.valueOf(dato.get("posicion")));
            jugador.setEdad((Integer) dato.get("edad"));
            jugador.setMedia((Integer) dato.get("media"));
            jugador.setRitmo((Integer) dato.get("ritmo"));
            jugador.setTiro((Integer) dato.get("tiro"));
            jugador.setPase((Integer) dato.get("pase"));
            jugador.setRegate((Integer) dato.get("regate"));
            jugador.setDefensa((Integer) dato.get("defensa"));
            jugador.setFisico((Integer) dato.get("fisico"));
            jugador.setFoto(String.valueOf(dato.get("imagenJugador")));
            jugador.setAltura("180 cm");
            jugador.setPeso("75 kg");
            jugadorRepository.save(jugador);
            nuevos++;
        }

        return Map.of("tipo", "jugadores reales", "nuevos", nuevos, "total", jugadorRepository.count(), "modo", footballApiService.getEstadoConfiguracion());
    }

    public Map<String, Object> importarCartasReales() {
        importarJugadoresReales();
        int nuevas = 0;
        for (Jugador jugador : jugadorRepository.findAll()) {
            if (jugador.getEquipo() == null) continue;
            boolean existe = cartaFUTRepository.findAll().stream()
                    .anyMatch(c -> nombreCarta(c).equalsIgnoreCase(jugador.getNombre())
                            && c.getClub() != null
                            && c.getClub().equalsIgnoreCase(jugador.getEquipo().getNombre()));
            if (existe) continue;
            CartaFUT carta = cartaGeneratorService.generarCarta(jugador);
            carta.setNombreJugador(jugador.getNombre());
            carta.setEnClub(true);
            cartaFUTRepository.save(carta);
            nuevas++;
        }
        return Map.of("tipo", "cartas reales", "nuevas", nuevas, "total", cartaFUTRepository.count(), "modo", footballApiService.getEstadoConfiguracion());
    }

    public Map<String, Object> importarTodoReal() {
        importarEquiposReales();
        importarJugadoresReales();
        importarCartasReales();
        return Map.of(
                "mensaje", "Importacion real completada",
                "equipos", equipoRepository.count(),
                "jugadores", jugadorRepository.count(),
                "cartas", cartaFUTRepository.count(),
                "modo", footballApiService.getEstadoConfiguracion()
        );
    }

    private void rellenarStats(Jugador jugador, String posicion, int media) {
        CartaFUT cartaTemporal = cartaGeneratorService.generarCarta(jugador);
        jugador.setRitmo(cartaTemporal.getRitmo());
        jugador.setTiro(cartaTemporal.getTiro());
        jugador.setPase(cartaTemporal.getPase());
        jugador.setRegate(cartaTemporal.getRegate());
        jugador.setDefensa(cartaTemporal.getDefensa());
        jugador.setFisico(cartaTemporal.getFisico());
        if ("DC".equals(posicion) || "EI".equals(posicion) || "ED".equals(posicion)) {
            jugador.setRitmo(Math.min(99, media + 8));
            jugador.setTiro(Math.min(99, media + 6));
        }
    }

    private Map<String, Object> resumen(String tipo, long total) {
        return Map.of("tipo", tipo, "total", total, "modo", "demo o API preparada");
    }

    private String nombreCarta(CartaFUT carta) {
        if (carta.getNombreJugador() != null && !carta.getNombreJugador().isBlank()) {
            return carta.getNombreJugador();
        }
        return carta.getJugador() == null ? "" : carta.getJugador().getNombre();
    }

    private String paisPorLiga(String liga) {
        return switch (liga) {
            case "LaLiga" -> "España";
            case "Premier League" -> "Inglaterra";
            case "Bundesliga" -> "Alemania";
            case "Serie A" -> "Italia";
            default -> "Europa";
        };
    }
}
