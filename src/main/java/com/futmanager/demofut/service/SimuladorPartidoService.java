package com.futmanager.demofut.service;

import com.futmanager.demofut.dto.PartidoSimuladoDTO;
import com.futmanager.demofut.entity.CartaFUT;
import com.futmanager.demofut.entity.PartidoSimulado;
import com.futmanager.demofut.entity.Plantilla;
import com.futmanager.demofut.entity.PlantillaCarta;
import com.futmanager.demofut.mapper.PartidoSimuladoMapper;
import com.futmanager.demofut.repository.PartidoSimuladoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class SimuladorPartidoService {

    private final Random random = new Random();

    @Autowired
    private PlantillaService plantillaService;

    @Autowired
    private PartidoSimuladoRepository partidoRepository;

    @Autowired
    private MonederoService monederoService;

    @Autowired
    private ClubUsuarioService clubUsuarioService;

    public PartidoSimuladoDTO simular(Long plantillaId) {
        Plantilla plantilla = plantillaService.findById(plantillaId)
                .orElseThrow(() -> new IllegalArgumentException("La plantilla no existe"));

        if (plantilla.getCartas().size() != 11) {
            throw new IllegalArgumentException("La plantilla debe tener 11 cartas para jugar");
        }

        plantillaService.calcularDatos(plantilla);

        String rival = rivalAleatorio();
        int mediaRival = 78 + random.nextInt(14);
        int ataqueUsuario = plantilla.getValoracionOfensiva() + plantilla.getQuimica() / 10 + random.nextInt(12);
        int defensaUsuario = plantilla.getValoracionDefensiva() + plantilla.getQuimica() / 12 + random.nextInt(12);
        int ataqueRival = mediaRival + random.nextInt(16);
        int defensaRival = mediaRival + random.nextInt(16);

        int golesUsuario = Math.max(0, (ataqueUsuario - defensaRival + 30) / 18 + random.nextInt(3));
        int golesRival = Math.max(0, (ataqueRival - defensaUsuario + 30) / 18 + random.nextInt(3));

        String ganador = golesUsuario > golesRival ? plantilla.getNombre()
                : golesRival > golesUsuario ? rival : "Empate";

        String mejores = mejoresJugadores(plantilla);
        String mvp = mejores.contains(",") ? mejores.substring(0, mejores.indexOf(",")) : mejores;
        String eventos = crearEventos(plantilla, rival, golesUsuario, golesRival);
        String resumen = plantilla.getNombre() + " " + golesUsuario + " - " + golesRival + " " + rival
                + ". Quimica: " + plantilla.getQuimica() + ". Ganador: " + ganador + ".";
        int monedasGanadas = premioMonedas(golesUsuario, golesRival);
        int experienciaGanada = 120 + golesUsuario * 30;

        PartidoSimulado partido = new PartidoSimulado();
        partido.setPlantilla(plantilla);
        partido.setNombrePlantilla(plantilla.getNombre());
        partido.setRival(rival);
        partido.setGolesUsuario(golesUsuario);
        partido.setGolesRival(golesRival);
        partido.setGanador(ganador);
        partido.setResumen(resumen);
        partido.setMejoresJugadores(mejores);
        partido.setMvp(mvp);
        partido.setMonedasGanadas(monedasGanadas);
        partido.setExperienciaGanada(experienciaGanada);
        partido.setEventos(eventos);
        partido.setFecha(LocalDateTime.now());

        monederoService.ganar(monedasGanadas);
        clubUsuarioService.sumarExperiencia(experienciaGanada);
        clubUsuarioService.registrarPartido(golesUsuario, golesRival);

        return PartidoSimuladoMapper.entityToDto(partidoRepository.save(partido));
    }

    public List<PartidoSimuladoDTO> findAllDTO() {
        return partidoRepository.findAll().stream()
                .map(PartidoSimuladoMapper::entityToDto)
                .toList();
    }

    public Optional<PartidoSimuladoDTO> findDTOById(Long id) {
        return partidoRepository.findById(id).map(PartidoSimuladoMapper::entityToDto);
    }

    private String rivalAleatorio() {
        List<String> rivales = List.of("FC Thunder", "Real Norte", "Atlético Costa", "Blue City", "Inter Capital");
        return rivales.get(random.nextInt(rivales.size()));
    }

    private int premioMonedas(int golesUsuario, int golesRival) {
        int premio = 500 + golesUsuario * 150;
        if (golesUsuario > golesRival) return premio + 1000;
        if (golesUsuario == golesRival) return premio + 400;
        return premio;
    }

    private String mejoresJugadores(Plantilla plantilla) {
        return plantilla.getCartas().stream()
                .map(PlantillaCarta::getCarta)
                .sorted(Comparator.comparing(CartaFUT::getMedia).reversed())
                .limit(3)
                .map(carta -> carta.getJugador() != null ? carta.getJugador().getNombre() : carta.getClub())
                .reduce("", (texto, club) -> texto.isBlank() ? club : texto + ", " + club);
    }

    private String crearEventos(Plantilla plantilla, String rival, int golesUsuario, int golesRival) {
        String atacante = plantilla.getCartas().stream()
                .map(PlantillaCarta::getCarta)
                .max(Comparator.comparing(CartaFUT::getTiro))
                .map(c -> c.getPosicion() + " de " + c.getClub())
                .orElse("tu delantero");

        StringBuilder eventos = new StringBuilder();
        eventos.append("Minuto 12: Primera ocasion clara de ").append(plantilla.getNombre()).append(".\n");
        if (golesUsuario > 0) {
            eventos.append("Minuto 24: Gol de ").append(atacante).append(".\n");
        }
        eventos.append("Minuto 38: Parada importante del portero.\n");
        if (golesRival > 0) {
            eventos.append("Minuto 58: Gol de ").append(rival).append(".\n");
        }
        if (golesUsuario > golesRival) {
            eventos.append("Minuto 80: Gol decisivo de tu plantilla.\n");
        } else if (golesRival > golesUsuario) {
            eventos.append("Minuto 82: El rival sentencia el partido.\n");
        } else {
            eventos.append("Minuto 88: Final igualado sin ganador claro.\n");
        }
        return eventos.toString();
    }
}
