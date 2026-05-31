package com.futmanager.demofut.config;

import com.futmanager.demofut.entity.CartaFUT;
import com.futmanager.demofut.entity.Equipo;
import com.futmanager.demofut.entity.Jugador;
import com.futmanager.demofut.repository.CartaFUTRepository;
import com.futmanager.demofut.repository.EquipoRepository;
import com.futmanager.demofut.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class DataLoader implements CommandLineRunner {

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private CartaFUTRepository cartaFUTRepository;

    @Override
    public void run(String... args) {
        System.out.println(">> DataLoader: revisando datos iniciales...");

        if (jugadorRepository.count() > 0) {
            System.out.println(">> DataLoader: ya hay jugadores, no se duplican datos pequenos.");
            return;
        }

        Equipo realMadrid = equipo("Real Madrid", "La Liga", "España");
        Equipo fcBarcelona = equipo("FC Barcelona", "La Liga", "España");
        Equipo manchesterCity = equipo("Manchester City", "Premier League", "Inglaterra");
        Equipo liverpool = equipo("Liverpool", "Premier League", "Inglaterra");
        Equipo psg = equipo("PSG", "Ligue 1", "Francia");
        Equipo bayern = equipo("Bayern Munich", "Bundesliga", "Alemania");
        Equipo inter = equipo("Inter Milan", "Serie A", "Italia");
        Equipo atletico = equipo("Atletico de Madrid", "La Liga", "España");

        jugadorConCarta("Vinicius Jr", "EI", "Brasil", 25, realMadrid, 92, 95, 84, 81, 93, 29, 75,
                "Especial", "Oro Rare", "#facc15", "Cazador", 180000);
        jugadorConCarta("Jude Bellingham", "MC", "Inglaterra", 22, realMadrid, 90, 82, 85, 88, 89, 78, 82,
                "Oro", "Oro Rare", "#facc15", "Motor", 120000);
        jugadorConCarta("Kylian Mbappe", "DC", "Francia", 27, realMadrid, 94, 97, 92, 84, 93, 39, 78,
                "Especial", "TOTY", "#60a5fa", "Cazador", 250000);
        jugadorConCarta("Lamine Yamal", "ED", "España", 18, fcBarcelona, 89, 90, 80, 85, 91, 30, 60,
                "Especial", "Promesa", "#d946ef", "Halcón", 90000);
        jugadorConCarta("Pedri", "MC", "España", 23, fcBarcelona, 88, 78, 75, 88, 90, 68, 70,
                "Oro", "Oro Rare", "#facc15", "Motor", 85000);
        jugadorConCarta("Robert Lewandowski", "DC", "Polonia", 37, fcBarcelona, 88, 76, 90, 78, 84, 44, 78,
                "Oro", "Oro Rare", "#facc15", "Cazador", 70000);
        jugadorConCarta("Erling Haaland", "DC", "Noruega", 25, manchesterCity, 94, 89, 95, 65, 82, 45, 88,
                "Especial", "Oro Rare", "#facc15", "Motor", 210000);
        jugadorConCarta("Kevin De Bruyne", "MC", "Belgica", 34, manchesterCity, 91, 74, 88, 94, 88, 64, 78,
                "Oro", "Oro Rare", "#facc15", "Maestro", 110000);
        jugadorConCarta("Phil Foden", "MCO", "Inglaterra", 25, manchesterCity, 89, 84, 86, 88, 91, 58, 66,
                "Oro", "Oro Rare", "#facc15", "Motor", 95000);
        jugadorConCarta("Mohamed Salah", "ED", "Egipto", 33, liverpool, 91, 89, 90, 82, 90, 45, 75,
                "Oro", "Oro Rare", "#facc15", "Cazador", 130000);
        jugadorConCarta("Virgil van Dijk", "DFC", "Paises Bajos", 34, liverpool, 90, 78, 60, 72, 72, 93, 88,
                "Oro", "Oro Rare", "#facc15", "Sombra", 100000);
        jugadorConCarta("Alisson Becker", "POR", "Brasil", 33, liverpool, 89, 60, 40, 65, 55, 88, 82,
                "Oro", "Oro Rare", "#facc15", "Guante", 65000);
        jugadorConCarta("Ousmane Dembele", "ED", "Francia", 28, psg, 88, 93, 84, 82, 90, 38, 62,
                "Especial", "IF", "#111827", "Halcón", 80000);
        jugadorConCarta("Achraf Hakimi", "LD", "Marruecos", 27, psg, 87, 92, 74, 80, 84, 80, 78,
                "Oro", "Oro Rare", "#facc15", "Sombra", 75000);
        jugadorConCarta("Harry Kane", "DC", "Inglaterra", 32, bayern, 91, 72, 93, 84, 83, 50, 83,
                "Oro", "Oro Rare", "#facc15", "Cazador", 115000);
        jugadorConCarta("Jamal Musiala", "MCO", "Alemania", 22, bayern, 89, 86, 82, 84, 93, 63, 65,
                "Especial", "Promesa", "#d946ef", "Motor", 105000);
        jugadorConCarta("Lautaro Martinez", "DC", "Argentina", 28, inter, 89, 82, 90, 76, 86, 56, 84,
                "Oro", "Oro Rare", "#facc15", "Halcón", 90000);
        jugadorConCarta("Nicolo Barella", "MC", "Italia", 28, inter, 87, 80, 78, 86, 87, 82, 83,
                "Oro", "Oro Rare", "#facc15", "Motor", 65000);
        jugadorConCarta("Antoine Griezmann", "SD", "Francia", 34, atletico, 88, 80, 87, 88, 89, 58, 72,
                "Oro", "Oro Rare", "#facc15", "Maestro", 70000);
        jugadorConCarta("Jan Oblak", "POR", "Eslovenia", 33, atletico, 88, 55, 35, 62, 50, 88, 80,
                "Oro", "Oro Rare", "#facc15", "Guante", 60000);

        System.out.println(">> DataLoader: datos iniciales listos.");
    }

    private Equipo equipo(String nombre, String liga, String pais) {
        return equipoRepository.findAll().stream()
                .filter(equipo -> equipo.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElseGet(() -> equipoRepository.save(new Equipo(null, nombre, liga, pais, null)));
    }

    private void jugadorConCarta(String nombre, String posicion, String nacionalidad, Integer edad,
                                 Equipo equipo, Integer media, Integer ritmo, Integer tiro,
                                 Integer pase, Integer regate, Integer defensa, Integer fisico,
                                 String tipoCarta, String rareza, String colorCarta,
                                 String estiloCarta, Integer precio) {
        Jugador jugador = jugadorRepository.findAll().stream()
                .filter(j -> j.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElseGet(Jugador::new);

        jugador.setNombre(nombre);
        jugador.setPosicion(posicion);
        jugador.setNacionalidad(nacionalidad);
        jugador.setEdad(edad);
        jugador.setEquipo(equipo);
        jugador.setLiga(equipo.getLiga());
        jugador.setMedia(media);
        jugador.setRitmo(ritmo);
        jugador.setTiro(tiro);
        jugador.setPase(pase);
        jugador.setRegate(regate);
        jugador.setDefensa(defensa);
        jugador.setFisico(fisico);
        jugador.setFoto("https://ui-avatars.com/api/?name=" + nombre.replace(" ", "+") + "&background=111827&color=ffffff");
        jugador.setAltura("180 cm");
        jugador.setPeso("75 kg");
        jugador = jugadorRepository.save(jugador);
        Long jugadorId = jugador.getId();

        CartaFUT carta = cartaFUTRepository.findAll().stream()
                .filter(c -> c.getJugador() != null && c.getJugador().getId().equals(jugadorId))
                .findFirst()
                .orElseGet(CartaFUT::new);

        carta.setJugador(jugador);
        carta.setNombreJugador(nombre);
        carta.setTipoCarta(tipoCarta);
        carta.setRareza(rareza);
        carta.setMedia(media);
        carta.setRitmo(ritmo);
        carta.setTiro(tiro);
        carta.setPase(pase);
        carta.setRegate(regate);
        carta.setDefensa(defensa);
        carta.setFisico(fisico);
        carta.setColorCarta(colorCarta);
        carta.setLiga(equipo.getLiga());
        carta.setClub(equipo.getNombre());
        carta.setNacionalidad(nacionalidad);
        carta.setPosicion(posicion);
        carta.setPrecioMonedas(precio);
        carta.setEstiloCarta(estiloCarta);
        carta.setImagenJugador("https://ui-avatars.com/api/?name=" + nombre.replace(" ", "+") + "&background=111827&color=ffffff");
        carta.setImagenEscudo("https://ui-avatars.com/api/?name=" + equipo.getNombre().replace(" ", "+") + "&background=0f766e&color=ffffff");
        carta.setImagenBandera("https://ui-avatars.com/api/?name=" + nacionalidad.substring(0, 2).toUpperCase() + "&background=1d4ed8&color=ffffff");
        cartaFUTRepository.save(carta);
    }
}
