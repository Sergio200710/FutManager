package com.futmanager.demofut;

import com.futmanager.demofut.entity.Equipo;
import com.futmanager.demofut.entity.Jugador;
import com.futmanager.demofut.repository.EquipoRepository;
import com.futmanager.demofut.repository.JugadorRepository;
import com.futmanager.demofut.service.EquipoService;
import com.futmanager.demofut.service.JugadorService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JugadorServiceTest {

    @Mock
    private JugadorRepository jugadorRepository;

    @Mock
    private EquipoRepository equipoRepository;

    @InjectMocks
    private JugadorService jugadorService;

    @InjectMocks
    private EquipoService equipoService;

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGuardarJugadorCorrectamente() {
        // Given
        Equipo equipo = new Equipo(1L, "Real Madrid", "La Liga", "España", new ArrayList<>());
        Jugador jugador = crearJugador(1L, "Vinicius", "EI", 90, 95, 80, 80, 90, 30, 70, equipo);
        when(jugadorRepository.save(any(Jugador.class))).thenReturn(jugador);

        // When
        Jugador guardado = jugadorService.save(jugador);

        // Then
        assertNotNull(guardado);
        assertEquals("Vinicius", guardado.getNombre());
    }

    @Test
    void testBuscarJugadorPorId() {
        // Given
        Jugador jugador = crearJugador(1L, "Vinicius", "EI", 90, 95, 80, 80, 90, 30, 70, null);
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));

        // When
        Optional<Jugador> encontrado = jugadorService.findById(1L);

        // Then
        assertTrue(encontrado.isPresent());
        assertEquals("Vinicius", encontrado.get().getNombre());
    }

    @Test
    void testActualizarMediaDeJugador() {
        // Given
        Jugador jugador = crearJugador(1L, "Vinicius", "EI", 90, 95, 80, 80, 90, 30, 70, null);
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(jugadorRepository.save(any(Jugador.class))).thenReturn(jugador);

        // When
        Jugador aActualizar = jugadorService.findById(1L).get();
        aActualizar.setMedia(99);
        Jugador actualizado = jugadorService.save(aActualizar);

        // Then
        assertEquals(99, actualizado.getMedia());
    }

    @Test
    void testEliminarJugador() {
        // Given
        doNothing().when(jugadorRepository).deleteById(1L);

        // When
        jugadorService.deleteById(1L);

        // Then
        verify(jugadorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFiltrarPorPosicion() {
        // Given
        List<Jugador> lista = new ArrayList<>();
        lista.add(crearJugador(1L, "Courtois", "POR", 90, 50, 50, 50, 50, 50, 50, null));
        when(jugadorRepository.findByPosicion("POR")).thenReturn(lista);

        // When
        List<Jugador> porteros = jugadorService.findByPosicion("POR");

        // Then
        assertFalse(porteros.isEmpty());
        assertEquals("POR", porteros.get(0).getPosicion());
    }

    @Test
    void testFiltrarPorMediaMinima() {
        // Given
        List<Jugador> lista = new ArrayList<>();
        lista.add(crearJugador(1L, "Haaland", "DC", 94, 89, 95, 65, 82, 45, 88, null));
        when(jugadorRepository.findByMediaGreaterThanEqual(85)).thenReturn(lista);

        // When
        List<Jugador> cracks = jugadorService.findByMediaMinima(85);

        // Then
        assertFalse(cracks.isEmpty());
        assertTrue(cracks.get(0).getMedia() >= 85);
    }

    @Test
    void testRechazarNombreVacio() {
        // Given
        Equipo equipo = new Equipo(1L, "Madrid", "Liga", "ES", new ArrayList<>());
        Jugador jugadorInvalido = crearJugador(1L, "", "POR", 90, 50, 50, 50, 50, 50, 50, equipo);

        // When
        Set<ConstraintViolation<Jugador>> violations = validator.validate(jugadorInvalido);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("vacío")));
    }

    @Test
    void testRechazarMediaFueraDeRango() {
        // Given
        Equipo equipo = new Equipo(1L, "Madrid", "Liga", "ES", new ArrayList<>());
        Jugador jugadorInvalido = crearJugador(1L, "Courtois", "POR", 150, 50, 50, 50, 50, 50, 50, equipo);

        // When
        Set<ConstraintViolation<Jugador>> violations = validator.validate(jugadorInvalido);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("máximo 99")));
    }

    @Test
    void testCrearEquipo() {
        // Given
        Equipo equipo = new Equipo(1L, "FC Barcelona", "La Liga", "España", new ArrayList<>());
        when(equipoRepository.save(any(Equipo.class))).thenReturn(equipo);

        // When
        Equipo creado = equipoService.save(equipo);

        // Then
        assertNotNull(creado);
        assertEquals("FC Barcelona", creado.getNombre());
    }

    @Test
    void testComprobarRelacionEquipoJugadores() {
        // Given
        Equipo equipo = new Equipo(1L, "Real Madrid", "La Liga", "España", new ArrayList<>());
        Jugador jugador = crearJugador(1L, "Vinicius", "EI", 90, 95, 80, 80, 90, 30, 70, equipo);
        equipo.getJugadores().add(jugador);

        // When
        List<Jugador> jugadoresDelEquipo = equipo.getJugadores();

        // Then
        assertAll("Relación Equipo-Jugadores",
                () -> assertFalse(jugadoresDelEquipo.isEmpty()),
                () -> assertEquals("Vinicius", jugadoresDelEquipo.get(0).getNombre()),
                () -> assertEquals("Real Madrid", jugadoresDelEquipo.get(0).getEquipo().getNombre())
        );
    }

    private Jugador crearJugador(Long id, String nombre, String posicion, Integer media,
                                 Integer ritmo, Integer tiro, Integer pase, Integer regate,
                                 Integer defensa, Integer fisico, Equipo equipo) {
        Jugador jugador = new Jugador();
        jugador.setId(id);
        jugador.setNombre(nombre);
        jugador.setPosicion(posicion);
        jugador.setNacionalidad("España");
        jugador.setEdad(25);
        jugador.setMedia(media);
        jugador.setRitmo(ritmo);
        jugador.setTiro(tiro);
        jugador.setPase(pase);
        jugador.setRegate(regate);
        jugador.setDefensa(defensa);
        jugador.setFisico(fisico);
        jugador.setEquipo(equipo);
        return jugador;
    }
}
