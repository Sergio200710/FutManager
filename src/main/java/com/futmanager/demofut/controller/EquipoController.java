package com.futmanager.demofut.controller;

import com.futmanager.demofut.dto.EquipoDTO;
import com.futmanager.demofut.service.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
@CrossOrigin(origins = "*")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @GetMapping
    public List<EquipoDTO> getAll() {
        return equipoService.findAllDTO();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipoDTO> getById(@PathVariable Long id) {
        return equipoService.findDTOById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/liga/{ligaId}")
    public List<EquipoDTO> getByLiga(@PathVariable Long ligaId) {
        return equipoService.findByLiga(ligaId);
    }

    @PostMapping
    public EquipoDTO create(@Valid @RequestBody EquipoDTO equipoDTO) {
        return equipoService.createDTO(equipoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipoDTO> update(@PathVariable Long id, @Valid @RequestBody EquipoDTO equipoDTO) {
        return equipoService.updateDTO(id, equipoDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (equipoService.findById(id).isPresent()) {
            equipoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
