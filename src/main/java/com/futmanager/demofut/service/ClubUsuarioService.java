package com.futmanager.demofut.service;

import com.futmanager.demofut.entity.ClubUsuario;
import com.futmanager.demofut.repository.ClubUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClubUsuarioService {

    private static final Long CLUB_ID = 1L;

    @Autowired
    private ClubUsuarioRepository clubRepository;

    public ClubUsuario getClub() {
        return clubRepository.findById(CLUB_ID)
                .orElseGet(() -> clubRepository.save(new ClubUsuario(CLUB_ID, "FutManager FC", 50000, 1, 0, 0, 0, 0, 0)));
    }

    public ClubUsuario update(ClubUsuario datos) {
        ClubUsuario club = getClub();
        if (datos.getNombreClub() != null && !datos.getNombreClub().isBlank()) {
            club.setNombreClub(datos.getNombreClub());
        }
        return clubRepository.save(club);
    }

    public ClubUsuario sumarMonedas(Integer cantidad) {
        ClubUsuario club = getClub();
        club.setMonedas(club.getMonedas() + valor(cantidad));
        return clubRepository.save(club);
    }

    public ClubUsuario restarMonedas(Integer cantidad) {
        ClubUsuario club = getClub();
        int valor = valor(cantidad);
        if (club.getMonedas() < valor) {
            throw new IllegalArgumentException("No tienes monedas suficientes");
        }
        club.setMonedas(club.getMonedas() - valor);
        return clubRepository.save(club);
    }

    public ClubUsuario sumarExperiencia(Integer cantidad) {
        ClubUsuario club = getClub();
        club.setExperiencia(club.getExperiencia() + valor(cantidad));
        while (club.getExperiencia() >= club.getNivel() * 1000) {
            club.setExperiencia(club.getExperiencia() - club.getNivel() * 1000);
            club.setNivel(club.getNivel() + 1);
        }
        return clubRepository.save(club);
    }

    public ClubUsuario registrarPartido(int golesUsuario, int golesRival) {
        ClubUsuario club = getClub();
        club.setPartidosJugados(valor(club.getPartidosJugados()) + 1);
        if (golesUsuario > golesRival) {
            club.setVictorias(valor(club.getVictorias()) + 1);
        } else if (golesUsuario == golesRival) {
            club.setEmpates(valor(club.getEmpates()) + 1);
        } else {
            club.setDerrotas(valor(club.getDerrotas()) + 1);
        }
        return clubRepository.save(club);
    }

    private int valor(Integer cantidad) {
        return cantidad == null ? 0 : Math.max(0, cantidad);
    }
}
