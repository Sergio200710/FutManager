package com.futmanager.demofut.service;

import com.futmanager.demofut.dto.PlantillaDTO;
import com.futmanager.demofut.entity.CartaFUT;
import com.futmanager.demofut.entity.Plantilla;
import com.futmanager.demofut.entity.PlantillaCarta;
import com.futmanager.demofut.mapper.PlantillaMapper;
import com.futmanager.demofut.repository.CartaFUTRepository;
import com.futmanager.demofut.repository.PlantillaCartaRepository;
import com.futmanager.demofut.repository.PlantillaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantillaService {

    @Autowired
    private PlantillaRepository plantillaRepository;

    @Autowired
    private CartaFUTRepository cartaFUTRepository;

    @Autowired
    private PlantillaCartaRepository plantillaCartaRepository;

    @Autowired
    private QuimicaService quimicaService;

    public List<PlantillaDTO> findAllDTO() {
        return plantillaRepository.findAll().stream()
                .map(PlantillaMapper::entityToDto)
                .toList();
    }

    public Optional<PlantillaDTO> findDTOById(Long id) {
        return plantillaRepository.findById(id).map(PlantillaMapper::entityToDto);
    }

    public Optional<Plantilla> findById(Long id) {
        return plantillaRepository.findById(id);
    }

    public PlantillaDTO createDTO(PlantillaDTO dto) {
        Plantilla plantilla = new Plantilla();
        plantilla.setNombre(dto.getNombre());
        plantilla.setFormacion(dto.getFormacion() == null || dto.getFormacion().isBlank() ? "4-3-3" : dto.getFormacion());
        calcularDatos(plantilla);
        return PlantillaMapper.entityToDto(plantillaRepository.save(plantilla));
    }

    public Optional<PlantillaDTO> updateDTO(Long id, PlantillaDTO dto) {
        return plantillaRepository.findById(id).map(plantilla -> {
            plantilla.setNombre(dto.getNombre());
            if (dto.getFormacion() != null && !dto.getFormacion().isBlank()) {
                plantilla.setFormacion(dto.getFormacion());
            }
            return PlantillaMapper.entityToDto(plantillaRepository.save(plantilla));
        });
    }

    public void deleteById(Long id) {
        plantillaRepository.deleteById(id);
    }

    public PlantillaDTO addCarta(Long plantillaId, Long cartaId) {
        Plantilla plantilla = plantillaRepository.findById(plantillaId)
                .orElseThrow(() -> new IllegalArgumentException("La plantilla no existe"));
        CartaFUT carta = cartaFUTRepository.findById(cartaId)
                .orElseThrow(() -> new IllegalArgumentException("La carta no existe"));

        if (plantilla.getCartas().size() >= 11) {
            throw new IllegalArgumentException("La plantilla ya tiene 11 titulares");
        }

        if (plantillaCartaRepository.findByPlantillaIdAndCartaId(plantillaId, cartaId).isPresent()) {
            throw new IllegalArgumentException("Esta carta ya esta en la plantilla");
        }

        PlantillaCarta plantillaCarta = new PlantillaCarta();
        plantillaCarta.setPlantilla(plantilla);
        plantillaCarta.setCarta(carta);
        plantilla.getCartas().add(plantillaCarta);

        calcularDatos(plantilla);
        return PlantillaMapper.entityToDto(plantillaRepository.save(plantilla));
    }

    public PlantillaDTO removeCarta(Long plantillaId, Long cartaId) {
        Plantilla plantilla = plantillaRepository.findById(plantillaId)
                .orElseThrow(() -> new IllegalArgumentException("La plantilla no existe"));
        PlantillaCarta plantillaCarta = plantillaCartaRepository.findByPlantillaIdAndCartaId(plantillaId, cartaId)
                .orElseThrow(() -> new IllegalArgumentException("La carta no esta en la plantilla"));

        plantilla.getCartas().remove(plantillaCarta);
        plantillaCartaRepository.delete(plantillaCarta);
        calcularDatos(plantilla);
        return PlantillaMapper.entityToDto(plantillaRepository.save(plantilla));
    }

    public void calcularDatos(Plantilla plantilla) {
        List<CartaFUT> cartas = plantilla.getCartas().stream()
                .map(PlantillaCarta::getCarta)
                .toList();

        if (cartas.isEmpty()) {
            plantilla.setMediaTotal(0);
            plantilla.setQuimica(0);
            plantilla.setValoracionOfensiva(0);
            plantilla.setValoracionDefensiva(0);
            plantilla.setValoracionGeneral(0);
            return;
        }

        int media = promedio(cartas.stream().map(CartaFUT::getMedia).toList());
        int ataque = promedio(cartas.stream().map(c -> mediaDe(c.getRitmo(), c.getTiro(), c.getPase(), c.getRegate())).toList());
        int defensa = promedio(cartas.stream().map(c -> mediaDe(c.getDefensa(), c.getFisico())).toList());
        int quimica = quimicaService.calcular(cartas).getTotal();

        plantilla.setMediaTotal(media);
        plantilla.setValoracionOfensiva(ataque);
        plantilla.setValoracionDefensiva(defensa);
        plantilla.setQuimica(quimica);
        plantilla.setValoracionGeneral(mediaDe(media, ataque, defensa, quimica));
    }

    public PlantillaDTO resumen(Long id) {
        Plantilla plantilla = plantillaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La plantilla no existe"));
        calcularDatos(plantilla);
        PlantillaDTO dto = PlantillaMapper.entityToDto(plantillaRepository.save(plantilla));
        List<CartaFUT> cartas = plantilla.getCartas().stream().map(PlantillaCarta::getCarta).toList();
        dto.setExplicacionQuimica(quimicaService.calcular(cartas).getExplicacion());
        return dto;
    }

    private int promedio(List<Integer> numeros) {
        return (int) Math.round(numeros.stream()
                .filter(n -> n != null)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0));
    }

    private int mediaDe(Integer... valores) {
        int suma = 0;
        int total = 0;
        for (Integer valor : valores) {
            if (valor != null) {
                suma += valor;
                total++;
            }
        }
        return total == 0 ? 0 : Math.round((float) suma / total);
    }
}
