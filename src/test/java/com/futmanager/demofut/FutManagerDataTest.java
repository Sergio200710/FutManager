package com.futmanager.demofut;

import com.futmanager.demofut.entity.CartaFUT;
import com.futmanager.demofut.entity.Equipo;
import com.futmanager.demofut.entity.Jugador;
import com.futmanager.demofut.entity.Liga;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FutManagerDataTest {

    @Test
    void testCrearLiga() {
        Liga liga = new Liga();
        liga.setId(1L);
        liga.setNombre("LaLiga");
        liga.setPais("España");

        assertEquals("LaLiga", liga.getNombre());
        assertEquals("España", liga.getPais());
    }

    @Test
    void testCrearEquipoAsociadoALiga() {
        Liga liga = new Liga();
        liga.setNombre("LaLiga");

        Equipo equipo = new Equipo();
        equipo.setNombre("Real Madrid");
        equipo.setLigaDatos(liga);
        equipo.setLiga(liga.getNombre()); // Nombre de la liga en el campo string también

        assertEquals("Real Madrid", equipo.getNombre());
        assertEquals("LaLiga", equipo.getLigaDatos().getNombre());
        assertEquals("LaLiga", equipo.getLiga());
        assertNotEquals("LaLiga", equipo.getNombre()); // Comprobar que un equipo no se guarda como liga
    }

    @Test
    void testCrearJugadorAsociadoAEquipo() {
        Equipo equipo = new Equipo();
        equipo.setNombre("Real Madrid");

        Jugador jugador = new Jugador();
        jugador.setNombre("Kylian Mbappé");
        jugador.setEquipo(equipo);

        assertEquals("Kylian Mbappé", jugador.getNombre());
        assertEquals("Real Madrid", jugador.getEquipo().getNombre());
    }

    @Test
    void testCrearCartaFUTAsociadaAJugador() {
        Jugador jugador = new Jugador();
        jugador.setNombre("Vinícius Jr.");

        CartaFUT carta = new CartaFUT();
        carta.setJugador(jugador);
        carta.setMedia(90);

        assertEquals(90, carta.getMedia());
        assertEquals("Vinícius Jr.", carta.getJugador().getNombre());
    }

    @Test
    void testRelacionesCompletas() {
        Liga liga = new Liga();
        liga.setNombre("Premier League");

        Equipo equipo = new Equipo();
        equipo.setNombre("Manchester City");
        equipo.setLigaDatos(liga);
        
        List<Equipo> equipos = new ArrayList<>();
        equipos.add(equipo);
        liga.setEquipos(equipos);

        Jugador jugador = new Jugador();
        jugador.setNombre("Erling Haaland");
        jugador.setEquipo(equipo);

        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador);
        equipo.setJugadores(jugadores);

        CartaFUT carta = new CartaFUT();
        carta.setJugador(jugador);
        carta.setMedia(91);

        assertEquals("Premier League", carta.getJugador().getEquipo().getLigaDatos().getNombre());
        assertEquals(1, liga.getEquipos().size());
        assertEquals(1, equipo.getJugadores().size());
    }
}
