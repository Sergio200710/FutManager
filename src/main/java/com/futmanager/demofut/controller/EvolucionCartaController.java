package com.futmanager.demofut.controller;

import com.futmanager.demofut.entity.EvolucionCarta;
import com.futmanager.demofut.service.EvolucionCartaService;
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
@RequestMapping("/api/evoluciones")
@CrossOrigin(origins = "*")
public class EvolucionCartaController {

    @Autowired
    private EvolucionCartaService evolucionService;

    @GetMapping
    public List<EvolucionCarta> getAll() {
        return evolucionService.findAll();
    }

    @PostMapping("/carta/{cartaId}")
    public ResponseEntity<?> evolucionar(@PathVariable Long cartaId) {
        try {
            return ResponseEntity.ok(evolucionService.evolucionar(cartaId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
