package com.futmanager.demofut;

import com.futmanager.demofut.dto.CartaFUTDTO;
import com.futmanager.demofut.dto.PartidoSimuladoDTO;
import com.futmanager.demofut.dto.PlantillaDTO;
import com.futmanager.demofut.entity.CartaFUT;
import com.futmanager.demofut.entity.ClubUsuario;
import com.futmanager.demofut.entity.Objetivo;
import com.futmanager.demofut.entity.Plantilla;
import com.futmanager.demofut.entity.PlantillaCarta;
import com.futmanager.demofut.repository.CartaFUTRepository;
import com.futmanager.demofut.repository.JugadorRepository;
import com.futmanager.demofut.repository.ObjetivoRepository;
import com.futmanager.demofut.repository.PartidoSimuladoRepository;
import com.futmanager.demofut.repository.PlantillaCartaRepository;
import com.futmanager.demofut.repository.PlantillaRepository;
import com.futmanager.demofut.service.CartaFUTGeneratorService;
import com.futmanager.demofut.service.CartaFUTService;
import com.futmanager.demofut.service.ClubUsuarioService;
import com.futmanager.demofut.service.FootballApiService;
import com.futmanager.demofut.service.MercadoService;
import com.futmanager.demofut.service.MonederoService;
import com.futmanager.demofut.service.ObjetivoService;
import com.futmanager.demofut.service.PlantillaService;
import com.futmanager.demofut.service.QuimicaService;
import com.futmanager.demofut.service.SimuladorPartidoService;
import com.futmanager.demofut.service.SobreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UltimateTeamServiceTest {

    @Mock
    private CartaFUTRepository cartaFUTRepository;

    @Mock
    private JugadorRepository jugadorRepository;

    @Mock
    private PlantillaRepository plantillaRepository;

    @Mock
    private PlantillaCartaRepository plantillaCartaRepository;

    @Mock
    private PartidoSimuladoRepository partidoRepository;

    @Mock
    private ObjetivoRepository objetivoRepository;

    @Mock
    private PlantillaService plantillaServiceMock;

    @Mock
    private MonederoService monederoService;

    @Mock
    private ClubUsuarioService clubUsuarioService;

    @Spy
    private QuimicaService quimicaService = new QuimicaService();

    @InjectMocks
    private CartaFUTService cartaFUTService;

    @InjectMocks
    private PlantillaService plantillaService;

    @InjectMocks
    private SimuladorPartidoService simuladorPartidoService;

    @InjectMocks
    private SobreService sobreService;

    @InjectMocks
    private MercadoService mercadoService;

    @InjectMocks
    private ObjetivoService objetivoService;

    private final CartaFUTGeneratorService cartaGeneratorService = new CartaFUTGeneratorService();

    @Test
    void testCrearCarta() {
        CartaFUTDTO dto = cartaDTO(1L);
        when(cartaFUTRepository.save(any(CartaFUT.class))).thenAnswer(inv -> {
            CartaFUT carta = inv.getArgument(0);
            carta.setId(1L);
            return carta;
        });

        CartaFUTDTO creada = cartaFUTService.createDTO(dto);

        assertNotNull(creada);
        assertEquals(90, creada.getMedia());
        assertEquals("Oro Rare", creada.getRareza());
    }

    @Test
    void testCrearPlantilla() {
        PlantillaDTO dto = new PlantillaDTO();
        dto.setNombre("Mi once");
        when(plantillaRepository.save(any(Plantilla.class))).thenAnswer(inv -> {
            Plantilla plantilla = inv.getArgument(0);
            plantilla.setId(1L);
            return plantilla;
        });

        PlantillaDTO creada = plantillaService.createDTO(dto);

        assertEquals("Mi once", creada.getNombre());
        assertEquals(0, creada.getMediaTotal());
    }

    @Test
    void testAnadirCartaAPlantilla() {
        Plantilla plantilla = plantilla("Mi once");
        CartaFUT carta = carta(1L);
        when(plantillaRepository.findById(1L)).thenReturn(Optional.of(plantilla));
        when(cartaFUTRepository.findById(1L)).thenReturn(Optional.of(carta));
        when(plantillaCartaRepository.findByPlantillaIdAndCartaId(1L, 1L)).thenReturn(Optional.empty());
        when(plantillaRepository.save(any(Plantilla.class))).thenAnswer(inv -> inv.getArgument(0));

        PlantillaDTO resultado = plantillaService.addCarta(1L, 1L);

        assertEquals(1, resultado.getCartas().size());
        assertEquals(90, resultado.getMediaTotal());
    }

    @Test
    void testEvitarCartaRepetida() {
        Plantilla plantilla = plantilla("Mi once");
        CartaFUT carta = carta(1L);
        PlantillaCarta plantillaCarta = new PlantillaCarta(1L, plantilla, carta, "MC", true);
        when(plantillaRepository.findById(1L)).thenReturn(Optional.of(plantilla));
        when(cartaFUTRepository.findById(1L)).thenReturn(Optional.of(carta));
        when(plantillaCartaRepository.findByPlantillaIdAndCartaId(1L, 1L)).thenReturn(Optional.of(plantillaCarta));

        assertThrows(IllegalArgumentException.class, () -> plantillaService.addCarta(1L, 1L));
    }

    @Test
    void testNoPermitirPartidoConPlantillaIncompleta() {
        Plantilla plantilla = plantilla("Mi once");
        when(plantillaServiceMock.findById(1L)).thenReturn(Optional.of(plantilla));

        assertThrows(IllegalArgumentException.class, () -> simuladorPartidoService.simular(1L));
    }

    @Test
    void testSimularPartidoConPlantillaValida() {
        Plantilla plantilla = plantilla("Mi once");
        for (long i = 1; i <= 11; i++) {
            plantilla.getCartas().add(new PlantillaCarta(i, plantilla, carta(i), "MC", true));
        }
        plantilla.setMediaTotal(88);
        plantilla.setQuimica(90);
        plantilla.setValoracionOfensiva(86);
        plantilla.setValoracionDefensiva(82);

        when(plantillaServiceMock.findById(1L)).thenReturn(Optional.of(plantilla));
        doNothing().when(plantillaServiceMock).calcularDatos(plantilla);
        when(partidoRepository.save(any())).thenAnswer(inv -> {
            var partido = inv.getArgument(0, com.futmanager.demofut.entity.PartidoSimulado.class);
            partido.setId(1L);
            return partido;
        });
        when(monederoService.ganar(anyInt())).thenReturn(new ClubUsuario(1L, "FutManager FC", 101000, 1, 0, 1, 1, 0, 0));

        PartidoSimuladoDTO partido = simuladorPartidoService.simular(1L);

        assertNotNull(partido);
        assertNotNull(partido.getResumen());
        assertTrue(partido.getEventos().contains("Minuto"));
    }

    @Test
    void testGenerarCartaDesdeJugador() {
        var jugador = jugador("Delantero Demo", "DC", 88);

        CartaFUT carta = cartaGeneratorService.generarCarta(jugador);

        assertEquals("DC", carta.getPosicion());
        assertTrue(carta.getMedia() >= 55 && carta.getMedia() <= 99);
        assertTrue(carta.getTiro() >= 80);
    }

    @Test
    void testAbrirSobre() {
        when(monederoService.gastar(anyInt())).thenReturn(new ClubUsuario(1L, "FutManager FC", 92500, 1, 0, 0, 0, 0, 0));
        when(cartaFUTRepository.findAll()).thenReturn(List.of(carta(1L), carta(2L), carta(3L), carta(4L), carta(5L)));

        var resultado = sobreService.abrir("oro");

        assertEquals("oro", resultado.getTipoSobre());
        assertEquals(7500, resultado.getCoste());
        assertEquals(5, resultado.getCartas().size());
    }

    @Test
    void testComprarCarta() {
        when(cartaFUTRepository.findById(1L)).thenReturn(Optional.of(carta(1L)));
        when(monederoService.gastar(10000)).thenReturn(new ClubUsuario(1L, "FutManager FC", 90000, 1, 0, 0, 0, 0, 0));

        var monedero = mercadoService.comprar(1L);

        assertEquals(90000, monedero.getMonedas());
    }

    @Test
    void testCompletarObjetivo() {
        Objetivo objetivo = new Objetivo(1L, "Compra una carta", "MERCADO", 0, 1, false, 1500, 100);
        when(objetivoRepository.findById(1L)).thenReturn(Optional.of(objetivo));
        when(objetivoRepository.save(any(Objetivo.class))).thenAnswer(inv -> inv.getArgument(0));

        Objetivo completado = objetivoService.completar(1L);

        assertTrue(completado.getCompletado());
        verify(clubUsuarioService).sumarMonedas(1500);
    }

    @Test
    void testImportarJugadoresDemo() {
        FootballApiService apiService = new FootballApiService();

        assertEquals(15, apiService.obtenerJugadores().size());
        assertEquals("Kylian Mbappe", apiService.obtenerJugadores().get(0).get("nombre"));
    }

    @Test
    void testFiltrarCartasPorLiga() {
        when(cartaFUTRepository.filtrar(eq("LaLiga"), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), any()))
                .thenReturn(new PageImpl<>(List.of(carta(1L))));

        Page<CartaFUTDTO> resultado = cartaFUTService.findAllDTOPage("LaLiga", null, null, null, null, null, null, PageRequest.of(0, 20));

        assertEquals(1, resultado.getTotalElements());
        assertEquals("La Liga", resultado.getContent().get(0).getLiga());
    }

    @Test
    void testFiltrarCartasPorPosicion() {
        when(cartaFUTRepository.filtrar(isNull(), isNull(), isNull(), eq("MC"), isNull(), isNull(), isNull(), any()))
                .thenReturn(new PageImpl<>(List.of(carta(1L))));

        Page<CartaFUTDTO> resultado = cartaFUTService.findAllDTOPage(null, null, null, "MC", null, null, null, PageRequest.of(0, 20));

        assertEquals("MC", resultado.getContent().get(0).getPosicion());
    }

    @Test
    void testPaginacionCartas() {
        when(cartaFUTRepository.filtrar(isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), any()))
                .thenReturn(new PageImpl<>(List.of(carta(1L), carta(2L)), PageRequest.of(0, 2), 10));

        Page<CartaFUTDTO> resultado = cartaFUTService.findAllDTOPage(null, null, null, null, null, null, null, PageRequest.of(0, 2));

        assertEquals(2, resultado.getContent().size());
        assertEquals(10, resultado.getTotalElements());
    }

    @Test
    void testCrearMuchasCartasSinError() {
        List<CartaFUT> cartas = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            cartas.add(cartaGeneratorService.generarCarta(jugador("Jugador " + i, "MC", 70 + (i % 20))));
        }

        assertEquals(100, cartas.size());
        assertTrue(cartas.stream().allMatch(carta -> carta.getMedia() >= 55));
    }

    private Plantilla plantilla(String nombre) {
        Plantilla plantilla = new Plantilla();
        plantilla.setId(1L);
        plantilla.setNombre(nombre);
        plantilla.setCartas(new ArrayList<>());
        return plantilla;
    }

    private CartaFUT carta(Long id) {
        CartaFUT carta = new CartaFUT();
        carta.setId(id);
        carta.setTipoCarta("Oro");
        carta.setRareza("Oro Rare");
        carta.setMedia(90);
        carta.setRitmo(80);
        carta.setTiro(85);
        carta.setPase(83);
        carta.setRegate(86);
        carta.setDefensa(70);
        carta.setFisico(78);
        carta.setColorCarta("#facc15");
        carta.setLiga("La Liga");
        carta.setClub("Real Madrid");
        carta.setNacionalidad("España");
        carta.setPosicion("MC");
        carta.setPrecioMonedas(10000);
        carta.setEstiloCarta("Motor");
        return carta;
    }

    private com.futmanager.demofut.entity.Jugador jugador(String nombre, String posicion, Integer media) {
        com.futmanager.demofut.entity.Equipo equipo = new com.futmanager.demofut.entity.Equipo(1L, "Real Madrid", "La Liga", "España", new ArrayList<>());
        com.futmanager.demofut.entity.Jugador jugador = new com.futmanager.demofut.entity.Jugador();
        jugador.setNombre(nombre);
        jugador.setPosicion(posicion);
        jugador.setNacionalidad("España");
        jugador.setEdad(25);
        jugador.setMedia(media);
        jugador.setLiga("La Liga");
        jugador.setEquipo(equipo);
        return jugador;
    }

    private CartaFUTDTO cartaDTO(Long id) {
        CartaFUTDTO dto = new CartaFUTDTO();
        dto.setId(id);
        dto.setTipoCarta("Oro");
        dto.setRareza("Oro Rare");
        dto.setMedia(90);
        dto.setRitmo(80);
        dto.setTiro(85);
        dto.setPase(83);
        dto.setRegate(86);
        dto.setDefensa(70);
        dto.setFisico(78);
        dto.setColorCarta("#facc15");
        dto.setLiga("La Liga");
        dto.setClub("Real Madrid");
        dto.setNacionalidad("España");
        dto.setPosicion("MC");
        dto.setPrecioMonedas(10000);
        dto.setEstiloCarta("Motor");
        return dto;
    }
}
