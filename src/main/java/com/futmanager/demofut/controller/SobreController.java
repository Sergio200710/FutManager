package com.futmanager.demofut.controller;

import com.futmanager.demofut.service.SobreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sobres")
@CrossOrigin(origins = "*")
public class SobreController {

    @Autowired
    private SobreService sobreService;

    @GetMapping
    public java.util.List<com.futmanager.demofut.entity.Sobre> getAll() {
        return sobreService.findAll();
    }

    @PostMapping("/abrir/{tipoSobre}")
    public ResponseEntity<?> abrir(@PathVariable String tipoSobre) {
        try {
            return ResponseEntity.ok(sobreService.abrir(tipoSobre));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
