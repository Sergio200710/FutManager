package com.futmanager.demofut.controller;

import com.futmanager.demofut.dto.LigaDTO;
import com.futmanager.demofut.service.LigaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ligas")
@CrossOrigin(origins = "*")
public class LigaController {

    @Autowired
    private LigaService ligaService;

    @GetMapping
    public List<LigaDTO> getAll() {
        return ligaService.findAllDTO();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LigaDTO> getById(@PathVariable Long id) {
        return ligaService.findDTOById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public LigaDTO create(@Valid @RequestBody LigaDTO ligaDTO) {
        return ligaService.createDTO(ligaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LigaDTO> update(@PathVariable Long id, @Valid @RequestBody LigaDTO ligaDTO) {
        return ligaService.updateDTO(id, ligaDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (ligaService.findById(id).isPresent()) {
            ligaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
