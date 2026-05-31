package com.futmanager.demofut.controller;

import com.futmanager.demofut.dto.CartaFUTDTO;
import com.futmanager.demofut.service.MercadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mercado")
@CrossOrigin(origins = "*")
public class MercadoController {

    @Autowired
    private MercadoService mercadoService;

    @GetMapping("/cartas")
    public Page<CartaFUTDTO> getCartas(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String posicion,
            @RequestParam(required = false) Integer mediaMin,
            @RequestParam(required = false) String liga,
            @RequestParam(required = false) String club,
            @RequestParam(required = false) String rareza,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return mercadoService.buscarCartas(nombre, posicion, mediaMin, liga, club, rareza,
                PageRequest.of(page, size, Sort.by("precioMonedas").ascending()));
    }

    @PostMapping("/comprar/{cartaId}")
    public ResponseEntity<?> comprar(@PathVariable Long cartaId) {
        try {
            return ResponseEntity.ok(mercadoService.comprar(cartaId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/vender/{cartaId}")
    public ResponseEntity<?> vender(@PathVariable Long cartaId) {
        try {
            return ResponseEntity.ok(mercadoService.vender(cartaId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
