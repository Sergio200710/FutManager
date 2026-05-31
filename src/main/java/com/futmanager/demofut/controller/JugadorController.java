package com.futmanager.demofut.controller;

import com.futmanager.demofut.dto.JugadorDTO;
import com.futmanager.demofut.service.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/jugadores")
@CrossOrigin(origins = "*")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;

    @GetMapping
    public Object getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String liga,
            @RequestParam(required = false) String equipo,
            @RequestParam(required = false) String nacionalidad,
            @RequestParam(required = false) String posicion,
            @RequestParam(required = false) Integer mediaMinima,
            @RequestParam(required = false) Long equipoId,
            @RequestParam(required = false) String buscar,
            @RequestParam(defaultValue = "media") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        if (page != null || size != null || liga != null || equipo != null || nacionalidad != null || buscar != null) {
            Sort sort = "asc".equalsIgnoreCase(direction)
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
            return jugadorService.findAllDTOPage(liga, equipo, nacionalidad, posicion, mediaMinima, buscar,
                    PageRequest.of(page == null ? 0 : page, size == null ? 20 : size, sort));
        }

        return jugadorService.findAllDTO(posicion, mediaMinima, equipoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JugadorDTO> getById(@PathVariable Long id) {
        return jugadorService.findDTOById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/equipo/{equipoId}")
    public List<JugadorDTO> getByEquipo(@PathVariable Long equipoId) {
        return jugadorService.findDTOByEquipo(equipoId);
    }

    @GetMapping("/liga/{ligaId}")
    public List<JugadorDTO> getByLiga(@PathVariable Long ligaId) {
        return jugadorService.findDTOByLiga(ligaId);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody JugadorDTO jugadorDTO) {
        try {
            return ResponseEntity.ok(jugadorService.createDTO(jugadorDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody JugadorDTO jugadorDTO) {
        try {
            return jugadorService.updateDTO(id, jugadorDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (jugadorService.findById(id).isPresent()) {
            jugadorService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
