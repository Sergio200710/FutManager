package com.futmanager.demofut.controller;

import com.futmanager.demofut.entity.ClubUsuario;
import com.futmanager.demofut.service.CartaFUTService;
import com.futmanager.demofut.service.MercadoService;
import com.futmanager.demofut.service.ClubUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/club")
@CrossOrigin(origins = "*")
public class ClubUsuarioController {

    @Autowired
    private ClubUsuarioService clubUsuarioService;

    @Autowired
    private CartaFUTService cartaFUTService;

    @Autowired
    private MercadoService mercadoService;

    @GetMapping
    public ClubUsuario getClub() {
        return clubUsuarioService.getClub();
    }

    @PutMapping
    public ClubUsuario update(@RequestBody ClubUsuario club) {
        return clubUsuarioService.update(club);
    }

    @PostMapping("/monedas/sumar")
    public ClubUsuario sumar(@RequestParam Integer cantidad) {
        return clubUsuarioService.sumarMonedas(cantidad);
    }

    @PostMapping("/monedas/restar")
    public ResponseEntity<?> restar(@RequestParam Integer cantidad) {
        try {
            return ResponseEntity.ok(clubUsuarioService.restarMonedas(cantidad));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/cartas")
    public java.util.List<com.futmanager.demofut.dto.CartaFUTDTO> getCartasClub() {
        return cartaFUTService.findMisCartas();
    }

    @PostMapping("/cartas/{cartaId}/vender")
    public ResponseEntity<?> venderCarta(@org.springframework.web.bind.annotation.PathVariable Long cartaId) {
        try {
            return ResponseEntity.ok(mercadoService.vender(cartaId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/cartas/{cartaId}/transferible")
    public ResponseEntity<?> transferible(@org.springframework.web.bind.annotation.PathVariable Long cartaId,
                                          @RequestParam Boolean transferible) {
        return cartaFUTService.cambiarTransferible(cartaId, transferible)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
