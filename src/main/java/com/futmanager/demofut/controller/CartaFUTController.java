package com.futmanager.demofut.controller;

import com.futmanager.demofut.dto.CartaFUTDTO;
import com.futmanager.demofut.service.CartaFUTService;
import com.futmanager.demofut.service.FootballApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/cartas")
@CrossOrigin(origins = "*")
public class CartaFUTController {

    @Autowired
    private CartaFUTService cartaFUTService;

    @Autowired
    private FootballApiService footballApiService;

    @GetMapping
    public Object getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String liga,
            @RequestParam(required = false) String club,
            @RequestParam(required = false) String nacionalidad,
            @RequestParam(required = false) String posicion,
            @RequestParam(required = false) String rareza,
            @RequestParam(required = false) Integer mediaMin,
            @RequestParam(required = false) String buscar,
            @RequestParam(defaultValue = "media") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        if (page == null && size == null && liga == null && club == null && nacionalidad == null
                && posicion == null && rareza == null && mediaMin == null && buscar == null) {
            return cartaFUTService.findAllDTO();
        }

        int pageNumber = page == null ? 0 : page;
        int pageSize = size == null ? 20 : size;
        Sort sort = "asc".equalsIgnoreCase(direction)
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Page<CartaFUTDTO> resultado = cartaFUTService.findAllDTOPage(
                liga, club, nacionalidad, posicion, rareza, mediaMin, buscar, PageRequest.of(pageNumber, pageSize, sort));
        return resultado;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartaFUTDTO> getById(@PathVariable Long id) {
        return cartaFUTService.findDTOById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/mis-cartas")
    public List<CartaFUTDTO> misCartas() {
        return cartaFUTService.findMisCartas();
    }

    @GetMapping("/buscar")
    public Page<CartaFUTDTO> buscar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String nombreJugador,
            @RequestParam(required = false) Long ligaId,
            @RequestParam(required = false) Long equipoId,
            @RequestParam(required = false) String club,
            @RequestParam(required = false) String liga,
            @RequestParam(required = false) String nacionalidad,
            @RequestParam(required = false) String posicion,
            @RequestParam(required = false) String rareza,
            @RequestParam(required = false) String tipoCarta,
            @RequestParam(required = false) Integer mediaMin,
            @RequestParam(required = false) Integer mediaMax,
            @RequestParam(required = false) Integer precioMin,
            @RequestParam(required = false) Integer precioMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "media") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        Sort sort = "asc".equalsIgnoreCase(direction) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        if (nombreJugador != null || ligaId != null || equipoId != null || tipoCarta != null) {
            return cartaFUTService.buscarNormalizadoDTO(
                    nombreJugador == null ? nombre : nombreJugador, ligaId, equipoId, nacionalidad, posicion, rareza,
                    tipoCarta, mediaMin, mediaMax, precioMin, precioMax, PageRequest.of(page, size, sort));
        }
        return cartaFUTService.buscarDTO(nombre, club, liga, nacionalidad, posicion, rareza,
                mediaMin, mediaMax, precioMin, precioMax, PageRequest.of(page, size, sort));
    }

    @GetMapping("/jugador/{jugadorId}")
    public List<CartaFUTDTO> getByJugador(@PathVariable Long jugadorId) {
        return cartaFUTService.findDTOByJugador(jugadorId);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CartaFUTDTO cartaFUTDTO) {
        try {
            return ResponseEntity.ok(cartaFUTService.createDTO(cartaFUTDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CartaFUTDTO cartaFUTDTO) {
        try {
            return cartaFUTService.updateDTO(id, cartaFUTDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (cartaFUTService.findById(id).isPresent()) {
            cartaFUTService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/importar-api")
    public ResponseEntity<?> importarApi() {
        List<CartaFUTDTO> cartas = footballApiService.obtenerCartasDemo();
        return ResponseEntity.ok(cartaFUTService.guardarCartasImportadas(cartas));
    }
}
