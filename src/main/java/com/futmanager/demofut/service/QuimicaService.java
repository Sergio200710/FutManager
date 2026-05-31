package com.futmanager.demofut.service;

import com.futmanager.demofut.dto.QuimicaDTO;
import com.futmanager.demofut.entity.CartaFUT;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuimicaService {

    public QuimicaDTO calcular(List<CartaFUT> cartas) {
        if (cartas == null || cartas.isEmpty()) {
            return new QuimicaDTO(0, "Sin cartas no hay quimica.");
        }

        int puntos = cartas.size() * 2;
        for (int i = 0; i < cartas.size(); i++) {
            puntos += 2;
            for (int j = i + 1; j < cartas.size(); j++) {
                if (igual(cartas.get(i).getClub(), cartas.get(j).getClub())) puntos += 3;
                if (igual(cartas.get(i).getLiga(), cartas.get(j).getLiga())) puntos += 2;
                if (igual(cartas.get(i).getNacionalidad(), cartas.get(j).getNacionalidad())) puntos += 1;
            }
        }

        int total = Math.min(100, puntos);
        String explicacion = total >= 80
                ? "Buena quimica porque varios jugadores comparten club, liga o nacionalidad."
                : total >= 50
                ? "Quimica correcta, pero se puede mejorar juntando mas jugadores de la misma liga."
                : "Quimica baja: mezcla demasiados clubes, ligas y nacionalidades.";
        return new QuimicaDTO(total, explicacion);
    }

    private boolean igual(String a, String b) {
        return a != null && b != null && a.equalsIgnoreCase(b);
    }
}
