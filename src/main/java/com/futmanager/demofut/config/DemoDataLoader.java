package com.futmanager.demofut.config;

import com.futmanager.demofut.entity.CartaFUT;
import com.futmanager.demofut.entity.Equipo;
import com.futmanager.demofut.entity.Jugador;
import com.futmanager.demofut.entity.Liga;
import com.futmanager.demofut.repository.CartaFUTRepository;
import com.futmanager.demofut.repository.EquipoRepository;
import com.futmanager.demofut.repository.JugadorRepository;
import com.futmanager.demofut.repository.LigaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DemoDataLoader implements CommandLineRunner {

    private final LigaRepository ligaRepository;
    private final EquipoRepository equipoRepository;
    private final JugadorRepository jugadorRepository;
    private final CartaFUTRepository cartaFUTRepository;

    public DemoDataLoader(LigaRepository ligaRepository, EquipoRepository equipoRepository,
                          JugadorRepository jugadorRepository, CartaFUTRepository cartaFUTRepository) {
        this.ligaRepository = ligaRepository;
        this.equipoRepository = equipoRepository;
        this.jugadorRepository = jugadorRepository;
        this.cartaFUTRepository = cartaFUTRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (ligaRepository.count() > 0) {
            System.out.println("Base de datos ya inicializada. Omitiendo carga de datos de demostración.");
            return;
        }

        System.out.println("Inicializando datos reales en la base de datos...");

        // Ligas
        Liga laLiga = crearLiga("LaLiga", "España", "2025/2026", "https://ui-avatars.com/api/?name=LaLiga&background=1d4ed8&color=ffffff");
        Liga premier = crearLiga("Premier League", "Inglaterra", "2025/2026", "https://ui-avatars.com/api/?name=Premier+League&background=1d4ed8&color=ffffff");
        Liga bundesliga = crearLiga("Bundesliga", "Alemania", "2025/2026", "https://ui-avatars.com/api/?name=Bundesliga&background=1d4ed8&color=ffffff");
        Liga serieA = crearLiga("Serie A", "Italia", "2025/2026", "https://ui-avatars.com/api/?name=Serie+A&background=1d4ed8&color=ffffff");
        Liga ligue1 = crearLiga("Ligue 1", "Francia", "2025/2026", "https://ui-avatars.com/api/?name=Ligue+1&background=1d4ed8&color=ffffff");
        Liga saudi = crearLiga("Saudi Pro League", "Arabia Saudi", "2025/2026", "https://ui-avatars.com/api/?name=Saudi&background=1d4ed8&color=ffffff");
        Liga mls = crearLiga("MLS", "Estados Unidos", "2025/2026", "https://ui-avatars.com/api/?name=MLS&background=1d4ed8&color=ffffff");

        // Equipos y Jugadores - LaLiga
        Equipo madrid = crearEquipo("Real Madrid", "Madrid", "Santiago Bernabéu", laLiga);
        crearJugadorYCarta("Kylian", "Mbappé", "Kylian Mbappé", 25, "Francia", "DC", madrid, 91, 97, 90, 82, 92, 36, 78);
        crearJugadorYCarta("Vinícius", "Jr.", "Vinícius Jr.", 24, "Brasil", "EI", madrid, 90, 95, 84, 81, 91, 29, 68);
        crearJugadorYCarta("Jude", "Bellingham", "Jude Bellingham", 21, "Inglaterra", "MCO", madrid, 90, 82, 85, 88, 88, 78, 82);
        crearJugadorYCarta("Federico", "Valverde", "Federico Valverde", 26, "Uruguay", "MC", madrid, 88, 88, 82, 84, 84, 80, 82);
        crearJugadorYCarta("Thibaut", "Courtois", "Thibaut Courtois", 32, "Bélgica", "POR", madrid, 89, 85, 89, 76, 93, 46, 85);

        Equipo barca = crearEquipo("FC Barcelona", "Barcelona", "Spotify Camp Nou", laLiga);
        crearJugadorYCarta("Lamine", "Yamal", "Lamine Yamal", 17, "España", "ED", barca, 82, 88, 76, 80, 86, 32, 54);
        crearJugadorYCarta("Pedro", "González", "Pedri", 21, "España", "MC", barca, 86, 78, 68, 88, 88, 68, 73);
        crearJugadorYCarta("Pablo", "Páez", "Gavi", 20, "España", "MC", barca, 84, 76, 68, 80, 84, 74, 81);
        crearJugadorYCarta("Robert", "Lewandowski", "Robert Lewandowski", 36, "Polonia", "DC", barca, 88, 72, 87, 80, 83, 44, 83);
        crearJugadorYCarta("Frenkie", "de Jong", "Frenkie de Jong", 27, "Países Bajos", "MC", barca, 87, 80, 69, 86, 87, 77, 78);

        Equipo atletico = crearEquipo("Atlético de Madrid", "Madrid", "Cívitas Metropolitano", laLiga);
        crearJugadorYCarta("Antoine", "Griezmann", "Antoine Griezmann", 33, "Francia", "DC", atletico, 88, 80, 86, 88, 88, 52, 73);
        crearJugadorYCarta("Jan", "Oblak", "Jan Oblak", 31, "Eslovenia", "POR", atletico, 88, 86, 90, 78, 89, 50, 87);
        crearJugadorYCarta("Jorge", "Resurrección", "Koke", 32, "España", "MC", atletico, 84, 68, 72, 86, 82, 80, 78);
        crearJugadorYCarta("Julián", "Álvarez", "Julián Álvarez", 24, "Argentina", "DC", atletico, 86, 86, 84, 82, 85, 54, 76);

        // Equipos y Jugadores - Premier League
        Equipo city = crearEquipo("Manchester City", "Manchester", "Etihad Stadium", premier);
        crearJugadorYCarta("Erling", "Haaland", "Erling Haaland", 24, "Noruega", "DC", city, 91, 89, 93, 66, 80, 45, 88);
        crearJugadorYCarta("Phil", "Foden", "Phil Foden", 24, "Inglaterra", "MCO", city, 88, 85, 82, 86, 90, 56, 60);
        crearJugadorYCarta("Kevin", "De Bruyne", "Kevin De Bruyne", 33, "Bélgica", "MC", city, 90, 72, 85, 94, 87, 65, 78);
        crearJugadorYCarta("Rodrigo", "Hernández", "Rodri", 28, "España", "MCD", city, 91, 58, 73, 86, 84, 85, 85);
        crearJugadorYCarta("Rúben", "Dias", "Rúben Dias", 27, "Portugal", "DFC", city, 88, 62, 39, 66, 68, 89, 87);

        Equipo liverpool = crearEquipo("Liverpool", "Liverpool", "Anfield", premier);
        crearJugadorYCarta("Mohamed", "Salah", "Mohamed Salah", 32, "Egipto", "ED", liverpool, 89, 89, 87, 81, 88, 45, 76);
        crearJugadorYCarta("Virgil", "van Dijk", "Virgil van Dijk", 33, "Países Bajos", "DFC", liverpool, 89, 78, 60, 71, 72, 89, 86);
        crearJugadorYCarta("Alisson", "Becker", "Alisson Becker", 32, "Brasil", "POR", liverpool, 89, 86, 85, 85, 89, 54, 90);
        crearJugadorYCarta("Trent", "Alexander-Arnold", "Trent Alexander-Arnold", 26, "Inglaterra", "LD", liverpool, 86, 76, 71, 90, 80, 80, 73);

        Equipo arsenal = crearEquipo("Arsenal", "Londres", "Emirates Stadium", premier);
        crearJugadorYCarta("Bukayo", "Saka", "Bukayo Saka", 23, "Inglaterra", "ED", arsenal, 87, 85, 82, 81, 87, 65, 75);
        crearJugadorYCarta("Martin", "Ødegaard", "Martin Ødegaard", 25, "Noruega", "MCO", arsenal, 89, 74, 80, 89, 89, 65, 68);
        crearJugadorYCarta("Declan", "Rice", "Declan Rice", 25, "Inglaterra", "MCD", arsenal, 87, 72, 70, 82, 78, 85, 84);
        crearJugadorYCarta("William", "Saliba", "William Saliba", 23, "Francia", "DFC", arsenal, 87, 82, 40, 68, 74, 87, 82);

        // Equipos y Jugadores - Bundesliga
        Equipo bayern = crearEquipo("Bayern Munich", "Munich", "Allianz Arena", bundesliga);
        crearJugadorYCarta("Harry", "Kane", "Harry Kane", 31, "Inglaterra", "DC", bayern, 90, 69, 93, 85, 83, 49, 83);
        crearJugadorYCarta("Jamal", "Musiala", "Jamal Musiala", 21, "Alemania", "MCO", bayern, 87, 85, 80, 82, 92, 58, 63);
        crearJugadorYCarta("Joshua", "Kimmich", "Joshua Kimmich", 29, "Alemania", "MCD", bayern, 86, 70, 72, 87, 83, 82, 78);
        crearJugadorYCarta("Manuel", "Neuer", "Manuel Neuer", 38, "Alemania", "POR", bayern, 86, 84, 85, 89, 88, 46, 86);

        Equipo leverkusen = crearEquipo("Bayer Leverkusen", "Leverkusen", "BayArena", bundesliga);
        crearJugadorYCarta("Florian", "Wirtz", "Florian Wirtz", 21, "Alemania", "MCO", leverkusen, 88, 83, 81, 88, 89, 56, 68);
        crearJugadorYCarta("Alejandro", "Grimaldo", "Alejandro Grimaldo", 29, "España", "LI", leverkusen, 86, 84, 76, 87, 86, 78, 70);
        crearJugadorYCarta("Granit", "Xhaka", "Granit Xhaka", 32, "Suiza", "MCD", leverkusen, 86, 52, 74, 88, 80, 80, 84);

        // Equipos y Jugadores - Serie A
        Equipo inter = crearEquipo("Inter Milan", "Milan", "San Siro", serieA);
        crearJugadorYCarta("Lautaro", "Martínez", "Lautaro Martínez", 27, "Argentina", "DC", inter, 89, 85, 88, 76, 86, 50, 84);
        crearJugadorYCarta("Nicolò", "Barella", "Nicolò Barella", 27, "Italia", "MC", inter, 87, 78, 75, 84, 85, 81, 82);
        crearJugadorYCarta("Alessandro", "Bastoni", "Alessandro Bastoni", 25, "Italia", "DFC", inter, 87, 74, 40, 75, 74, 87, 83);

        Equipo juventus = crearEquipo("Juventus", "Turín", "Allianz Stadium", serieA);
        crearJugadorYCarta("Dušan", "Vlahović", "Dušan Vlahović", 24, "Serbia", "DC", juventus, 84, 80, 85, 66, 78, 28, 82);
        crearJugadorYCarta("Federico", "Chiesa", "Federico Chiesa", 27, "Italia", "EI", juventus, 84, 91, 81, 78, 86, 45, 73);
        crearJugadorYCarta("Gleison", "Bremer", "Bremer", 27, "Brasil", "DFC", juventus, 86, 82, 45, 58, 62, 86, 85);

        // Equipos y Jugadores - Ligue 1
        Equipo psg = crearEquipo("PSG", "París", "Parc des Princes", ligue1);
        crearJugadorYCarta("Ousmane", "Dembélé", "Ousmane Dembélé", 27, "Francia", "ED", psg, 86, 93, 77, 82, 88, 36, 56);
        crearJugadorYCarta("Achraf", "Hakimi", "Achraf Hakimi", 26, "Marruecos", "LD", psg, 84, 92, 74, 80, 81, 76, 78);
        crearJugadorYCarta("Gianluigi", "Donnarumma", "Gianluigi Donnarumma", 25, "Italia", "POR", psg, 89, 90, 84, 79, 89, 52, 85);

        Equipo monaco = crearEquipo("Monaco", "Mónaco", "Stade Louis II", ligue1);
        crearJugadorYCarta("Aleksandr", "Golovin", "Aleksandr Golovin", 28, "Rusia", "MCO", monaco, 81, 76, 78, 82, 83, 62, 65);
        crearJugadorYCarta("Folarin", "Balogun", "Folarin Balogun", 23, "Estados Unidos", "DC", monaco, 79, 85, 78, 66, 78, 28, 70);

        // Equipos y Jugadores - Saudi Pro League
        Equipo alNassr = crearEquipo("Al Nassr", "Riad", "Al-Awwal Park", saudi);
        crearJugadorYCarta("Cristiano", "Ronaldo", "Cristiano Ronaldo", 39, "Portugal", "DC", alNassr, 86, 77, 88, 75, 79, 34, 74);
        crearJugadorYCarta("Sadio", "Mané", "Sadio Mané", 32, "Senegal", "EI", alNassr, 84, 85, 80, 78, 84, 44, 76);
        crearJugadorYCarta("Aymeric", "Laporte", "Aymeric Laporte", 30, "España", "DFC", alNassr, 83, 62, 50, 72, 68, 84, 78);

        Equipo alHilal = crearEquipo("Al Hilal", "Riad", "Kingdom Arena", saudi);
        crearJugadorYCarta("Neymar", "Jr.", "Neymar Jr.", 32, "Brasil", "EI", alHilal, 87, 83, 81, 85, 92, 37, 58);
        crearJugadorYCarta("Aleksandar", "Mitrović", "Aleksandar Mitrović", 30, "Serbia", "DC", alHilal, 84, 65, 84, 68, 76, 40, 88);
        crearJugadorYCarta("Rúben", "Neves", "Rúben Neves", 27, "Portugal", "MCD", alHilal, 84, 60, 75, 86, 78, 76, 72);

        // Equipos y Jugadores - MLS
        Equipo miami = crearEquipo("Inter Miami", "Miami", "Chase Stadium", mls);
        crearJugadorYCarta("Lionel", "Messi", "Lionel Messi", 37, "Argentina", "ED", miami, 88, 79, 87, 89, 92, 24, 64);
        crearJugadorYCarta("Luis", "Suárez", "Luis Suárez", 37, "Uruguay", "DC", miami, 82, 65, 84, 76, 78, 42, 80);
        crearJugadorYCarta("Sergio", "Busquets", "Sergio Busquets", 36, "España", "MCD", miami, 81, 40, 60, 80, 78, 76, 70);
        crearJugadorYCarta("Jordi", "Alba", "Jordi Alba", 35, "España", "LI", miami, 82, 80, 68, 80, 81, 76, 70);

        System.out.println("Carga de datos reales completada con éxito.");
    }

    private Liga crearLiga(String nombre, String pais, String temporada, String logo) {
        Liga liga = new Liga();
        liga.setNombre(nombre);
        liga.setPais(pais);
        liga.setTemporada(temporada);
        liga.setLogo(logo);
        liga.setLogoUrl(logo);
        liga.setNivel(1);
        return ligaRepository.save(liga);
    }

    private Equipo crearEquipo(String nombre, String ciudad, String estadio, Liga liga) {
        Equipo equipo = new Equipo();
        equipo.setNombre(nombre);
        equipo.setCiudad(ciudad);
        equipo.setEstadio(estadio);
        equipo.setPais(liga.getPais());
        equipo.setLiga(liga.getNombre());
        equipo.setLigaDatos(liga);
        equipo.setEscudo("https://ui-avatars.com/api/?name=" + nombre.replace(" ", "+") + "&background=random&color=ffffff");
        return equipoRepository.save(equipo);
    }

    private void crearJugadorYCarta(String nombre, String apellido, String nombreCompleto, int edad, String nacionalidad, 
                                    String posicion, Equipo equipo, int media, int ritmo, int tiro, int pase, 
                                    int regate, int defensa, int fisico) {
        Jugador jugador = new Jugador();
        jugador.setNombre(nombreCompleto);
        jugador.setApellido(apellido);
        jugador.setNombreCompleto(nombreCompleto);
        jugador.setEdad(edad);
        jugador.setNacionalidad(nacionalidad);
        jugador.setPosicion(posicion);
        jugador.setLiga(equipo.getLiga());
        jugador.setEquipo(equipo);
        jugador.setMedia(media);
        jugador.setRitmo(ritmo);
        jugador.setTiro(tiro);
        jugador.setPase(pase);
        jugador.setRegate(regate);
        jugador.setDefensa(defensa);
        jugador.setFisico(fisico);
        jugador.setFoto("https://ui-avatars.com/api/?name=" + nombreCompleto.replace(" ", "+") + "&background=random&color=ffffff");
        jugador = jugadorRepository.save(jugador);

        CartaFUT carta = new CartaFUT();
        carta.setJugador(jugador);
        carta.setNombreJugador(nombreCompleto);
        carta.setClub(equipo.getNombre());
        carta.setLiga(equipo.getLiga());
        carta.setNacionalidad(nacionalidad);
        carta.setPosicion(posicion);
        carta.setPosicionCarta(posicion);
        carta.setMedia(media);
        carta.setRitmo(ritmo);
        carta.setTiro(tiro);
        carta.setPase(pase);
        carta.setRegate(regate);
        carta.setDefensa(defensa);
        carta.setFisico(fisico);
        carta.setRareza(media >= 85 ? "Oro raro" : "Oro");
        carta.setTipoCarta("Base");
        carta.setColorCarta("#ffffff");
        carta.setEstiloCarta("Básico");
        carta.setPrecioMonedas(media * 100);
        carta.setTransferible(true);
        carta.setImagenJugador(jugador.getFoto());
        carta.setImagenEscudo(equipo.getEscudo());
        carta.setImagenBandera("https://ui-avatars.com/api/?name=" + nacionalidad.substring(0, 2) + "&background=random");
        cartaFUTRepository.save(carta);
    }
}
