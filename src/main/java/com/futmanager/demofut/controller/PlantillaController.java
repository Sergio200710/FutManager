package com.futmanager.demofut.controller;

import com.futmanager.demofut.dto.PlantillaDTO;
import com.futmanager.demofut.service.PlantillaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/plantillas")
@CrossOrigin(origins = "*")
public class PlantillaController {

    @Autowired
    private PlantillaService plantillaService;

    @GetMapping
    public List<PlantillaDTO> getAll() {
        return plantillaService.findAllDTO();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantillaDTO> getById(@PathVariable Long id) {
        return plantillaService.findDTOById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/resumen")
    public ResponseEntity<?> resumen(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(plantillaService.resumen(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public PlantillaDTO create(@Valid @RequestBody PlantillaDTO plantillaDTO) {
        return plantillaService.createDTO(plantillaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantillaDTO> update(@PathVariable Long id, @Valid @RequestBody PlantillaDTO plantillaDTO) {
        return plantillaService.updateDTO(id, plantillaDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (plantillaService.findById(id).isPresent()) {
            plantillaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/cartas/{cartaId}")
    public ResponseEntity<?> addCarta(@PathVariable Long id, @PathVariable Long cartaId) {
        try {
            return ResponseEntity.ok(plantillaService.addCarta(id, cartaId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/cartas/{cartaId}")
    public ResponseEntity<?> removeCarta(@PathVariable Long id, @PathVariable Long cartaId) {
        try {
            return ResponseEntity.ok(plantillaService.removeCarta(id, cartaId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
