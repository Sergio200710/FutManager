package com.futmanager.demofut.service;

import com.futmanager.demofut.entity.Objetivo;
import com.futmanager.demofut.repository.ObjetivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjetivoService {

    @Autowired
    private ObjetivoRepository objetivoRepository;

    @Autowired
    private ClubUsuarioService clubUsuarioService;

    public List<Objetivo> findAll() {
        crearObjetivosBase();
        return objetivoRepository.findAll();
    }

    public Objetivo completar(Long id) {
        Objetivo objetivo = objetivoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El objetivo no existe"));
        if (!Boolean.TRUE.equals(objetivo.getCompletado())) {
            objetivo.setCompletado(true);
            clubUsuarioService.sumarMonedas(objetivo.getRecompensaMonedas());
            clubUsuarioService.sumarExperiencia(250);
        }
        return objetivoRepository.save(objetivo);
    }

    private void crearObjetivosBase() {
        if (objetivoRepository.count() > 0) return;
        objetivoRepository.save(new Objetivo(null, "Gana 1 partido", "PARTIDOS", 0, 1, false, 1000, 150));
        objetivoRepository.save(new Objetivo(null, "Gana 3 partidos", "PARTIDOS", 0, 3, false, 3000, 300));
        objetivoRepository.save(new Objetivo(null, "Crea una plantilla con quimica 80", "PLANTILLA", 0, 1, false, 2500, 250));
        objetivoRepository.save(new Objetivo(null, "Abre 3 sobres", "SOBRES", 0, 3, false, 2000, 200));
        objetivoRepository.save(new Objetivo(null, "Compra una carta en el mercado", "MERCADO", 0, 1, false, 1500, 150));
    }
}
