package com.futmanager.demofut.service;

import com.futmanager.demofut.dto.MonederoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonederoService {

    @Autowired
    private ClubUsuarioService clubUsuarioService;

    public MonederoDTO getMonedero() {
        return new MonederoDTO(clubUsuarioService.getClub().getMonedas());
    }

    public com.futmanager.demofut.entity.ClubUsuario gastar(Integer cantidad) {
        return clubUsuarioService.restarMonedas(cantidad);
    }

    public com.futmanager.demofut.entity.ClubUsuario ganar(Integer cantidad) {
        return clubUsuarioService.sumarMonedas(cantidad);
    }
}
