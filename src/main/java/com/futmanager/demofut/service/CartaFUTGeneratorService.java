package com.futmanager.demofut.service;

import com.futmanager.demofut.entity.CartaFUT;
import com.futmanager.demofut.entity.Jugador;
import org.springframework.stereotype.Service;

@Service
public class CartaFUTGeneratorService {

    public CartaFUT generarCarta(Jugador jugador) {
        CartaFUT carta = new CartaFUT();
        carta.setJugador(jugador);
        carta.setNombreJugador(jugador.getNombre());
        carta.setTipoCarta(calcularTipoCarta(jugador.getMedia()));
        carta.setRareza(calcularRareza(jugador.getMedia()));
        carta.setMedia(limitar(jugador.getMedia(), 55, 99));
        carta.setRitmo(limitar(valor(jugador.getRitmo(), statPorPosicion(jugador.getPosicion(), "ritmo")), 1, 99));
        carta.setTiro(limitar(valor(jugador.getTiro(), statPorPosicion(jugador.getPosicion(), "tiro")), 1, 99));
        carta.setPase(limitar(valor(jugador.getPase(), statPorPosicion(jugador.getPosicion(), "pase")), 1, 99));
        carta.setRegate(limitar(valor(jugador.getRegate(), statPorPosicion(jugador.getPosicion(), "regate")), 1, 99));
        carta.setDefensa(limitar(valor(jugador.getDefensa(), statPorPosicion(jugador.getPosicion(), "defensa")), 1, 99));
        carta.setFisico(limitar(valor(jugador.getFisico(), statPorPosicion(jugador.getPosicion(), "fisico")), 1, 99));
        carta.setLiga(jugador.getLiga());
        carta.setClub(jugador.getEquipo() == null ? "Sin club" : jugador.getEquipo().getNombre());
        carta.setNacionalidad(jugador.getNacionalidad());
        carta.setPosicion(jugador.getPosicion());
        carta.setPrecioMonedas(calcularPrecio(carta.getMedia(), carta.getRareza()));
        carta.setColorCarta(calcularColor(carta.getRareza()));
        carta.setEstiloCarta(calcularEstilo(jugador.getPosicion()));
        carta.setImagenJugador(valorTexto(jugador.getFoto(), avatar(jugador.getNombre(), "111827")));
        carta.setImagenEscudo(jugador.getEquipo() == null ? "" : valorTexto(jugador.getEquipo().getEscudo(), avatar(jugador.getEquipo().getNombre(), "0f766e")));
        carta.setImagenBandera("https://ui-avatars.com/api/?name=" + iniciales(jugador.getNacionalidad()) + "&background=1d4ed8&color=ffffff");
        return carta;
    }

    public String calcularRareza(Integer media) {
        int valor = limitar(media, 55, 99);
        if (valor <= 64) return valor >= 62 ? "Bronce raro" : "Bronce comun";
        if (valor <= 74) return valor >= 72 ? "Plata rara" : "Plata comun";
        if (valor <= 84) return "Oro comun";
        if (valor <= 89) return "Oro raro";
        if (valor <= 93) return "Especial";
        if (valor <= 96) return "Icono";
        return "TOTS";
    }

    private String calcularTipoCarta(Integer media) {
        int valor = limitar(media, 55, 99);
        if (valor >= 94) return "Icono";
        if (valor >= 90) return "Especial";
        if (valor >= 75) return "Oro";
        if (valor >= 65) return "Plata";
        return "Bronce";
    }

    private int statPorPosicion(String posicion, String stat) {
        String pos = posicion == null ? "" : posicion.toUpperCase();
        if (pos.contains("POR")) return switch (stat) {
            case "ritmo" -> 45; case "tiro" -> 30; case "pase" -> 58; case "regate" -> 45; case "defensa" -> 78; default -> 72;
        };
        if (pos.contains("DFC") || pos.contains("LD") || pos.contains("LI")) return switch (stat) {
            case "ritmo" -> 68; case "tiro" -> 45; case "pase" -> 62; case "regate" -> 60; case "defensa" -> 82; default -> 80;
        };
        if (pos.contains("MC") || pos.contains("MCD") || pos.contains("MCO")) return switch (stat) {
            case "ritmo" -> 72; case "tiro" -> 70; case "pase" -> 82; case "regate" -> 78; case "defensa" -> 68; default -> 74;
        };
        return switch (stat) {
            case "ritmo" -> 84; case "tiro" -> 82; case "pase" -> 72; case "regate" -> 84; case "defensa" -> 38; default -> 72;
        };
    }

    private Integer calcularPrecio(Integer media, String rareza) {
        int precio = limitar(media, 55, 99) * 900;
        if (rareza != null && (rareza.contains("Especial") || rareza.contains("Icono") || rareza.contains("TOTS"))) precio *= 3;
        return precio;
    }

    private String calcularColor(String rareza) {
        if (rareza == null) return "#facc15";
        if (rareza.contains("Bronce")) return "#b45309";
        if (rareza.contains("Plata")) return "#cbd5e1";
        if (rareza.contains("Icono")) return "#f8fafc";
        if (rareza.contains("Especial") || rareza.contains("TOTS")) return "#60a5fa";
        return "#facc15";
    }

    private String calcularEstilo(String posicion) {
        String pos = posicion == null ? "" : posicion.toUpperCase();
        if (pos.contains("POR")) return "Guante";
        if (pos.contains("DF") || pos.contains("LD") || pos.contains("LI")) return "Sombra";
        if (pos.contains("MC")) return "Motor";
        return "Cazador";
    }

    private Integer valor(Integer valor, Integer alternativa) {
        return valor == null ? alternativa : valor;
    }

    private String valorTexto(String valor, String alternativa) {
        return valor == null || valor.isBlank() ? alternativa : valor;
    }

    private int limitar(Integer valor, int min, int max) {
        int numero = valor == null ? min : valor;
        return Math.max(min, Math.min(max, numero));
    }

    private String avatar(String texto, String color) {
        return "https://ui-avatars.com/api/?name=" + texto.replace(" ", "+") + "&background=" + color + "&color=ffffff";
    }

    private String iniciales(String texto) {
        if (texto == null || texto.length() < 2) return "NA";
        return texto.substring(0, 2).toUpperCase();
    }
}
