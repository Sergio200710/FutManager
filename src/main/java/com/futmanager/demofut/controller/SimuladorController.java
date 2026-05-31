package com.futmanager.demofut.controller;

import com.futmanager.demofut.dto.PartidoSimuladoDTO;
import com.futmanager.demofut.service.SimuladorPartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class SimuladorController {

    @Autowired
    private SimuladorPartidoService simuladorPartidoService;

    @PostMapping("/api/simulador/plantilla/{plantillaId}")
    public ResponseEntity<?> simular(@PathVariable Long plantillaId) {
        try {
            return ResponseEntity.ok(simuladorPartidoService.simular(plantillaId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/partidos")
    public List<PartidoSimuladoDTO> getPartidos() {
        return simuladorPartidoService.findAllDTO();
    }

    @GetMapping("/api/partidos/{id}")
    public ResponseEntity<PartidoSimuladoDTO> getPartido(@PathVariable Long id) {
        return simuladorPartidoService.findDTOById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
