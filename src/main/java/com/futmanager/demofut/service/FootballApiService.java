package com.futmanager.demofut.service;

import com.futmanager.demofut.dto.CartaFUTDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FootballApiService {

    @Value("${football.api.key:TU_API_KEY_AQUI}")
    private String apiKey;

    @Value("${football.api.host:v3.football.api-sports.io}")
    private String apiHost;

    @Value("${football.api.url:https://v3.football.api-sports.io}")
    private String apiUrl;

    @Value("${football.api.enabled:false}")
    private boolean apiEnabled;

    @Value("${football.api.season:2025}")
    private String season;

    private static final List<Map<String, Object>> JUGADORES_REALES_DEMO = List.of(
            jugadorReal("Kylian Mbappe", "Real Madrid", "LaLiga", "Francia", "DC", 27, 94, 97, 92, 84, 93, 39, 78, 278),
            jugadorReal("Vinicius Jr", "Real Madrid", "LaLiga", "Brasil", "EI", 25, 92, 95, 84, 81, 93, 29, 75, 276),
            jugadorReal("Jude Bellingham", "Real Madrid", "LaLiga", "Inglaterra", "MC", 22, 90, 82, 85, 88, 89, 78, 82, 762),
            jugadorReal("Erling Haaland", "Manchester City", "Premier League", "Noruega", "DC", 25, 94, 89, 95, 65, 82, 45, 88, 1100),
            jugadorReal("Lamine Yamal", "FC Barcelona", "LaLiga", "España", "ED", 18, 89, 90, 80, 85, 91, 30, 60, 2201),
            jugadorReal("Pedri", "FC Barcelona", "LaLiga", "España", "MC", 23, 88, 78, 75, 88, 90, 68, 70, 583),
            jugadorReal("Rodri", "Manchester City", "Premier League", "España", "MCD", 29, 91, 66, 80, 86, 84, 89, 85, 44),
            jugadorReal("Harry Kane", "Bayern Munich", "Bundesliga", "Inglaterra", "DC", 32, 91, 72, 93, 84, 83, 50, 83, 184),
            jugadorReal("Mohamed Salah", "Liverpool", "Premier League", "Egipto", "ED", 33, 91, 89, 90, 82, 90, 45, 75, 306),
            jugadorReal("Florian Wirtz", "Liverpool", "Premier League", "Alemania", "MCO", 22, 89, 82, 84, 89, 91, 55, 68, 1920),
            jugadorReal("Jamal Musiala", "Bayern Munich", "Bundesliga", "Alemania", "MCO", 22, 89, 86, 82, 84, 93, 63, 65, 2295),
            jugadorReal("Lautaro Martinez", "Inter Milan", "Serie A", "Argentina", "DC", 28, 89, 82, 90, 76, 86, 56, 84, 341),
            jugadorReal("Federico Valverde", "Real Madrid", "LaLiga", "Uruguay", "MC", 27, 88, 88, 82, 86, 84, 84, 86, 468),
            jugadorReal("Bukayo Saka", "Arsenal", "Premier League", "Inglaterra", "ED", 24, 88, 86, 85, 82, 89, 60, 70, 1460),
            jugadorReal("Phil Foden", "Manchester City", "Premier League", "Inglaterra", "MCO", 25, 89, 84, 86, 88, 91, 58, 66, 633)
    );

    public List<Map<String, Object>> obtenerLigas() {
        return datosDemo("ligas", 10);
    }

    public List<Map<String, Object>> obtenerEquipos() {
        return datosDemo("equipos", 100);
    }

    public List<Map<String, Object>> obtenerJugadores() {
        return importarJugadoresReales();
    }

    public List<Map<String, Object>> obtenerEquiposPorLiga(String liga) {
        return JUGADORES_REALES_DEMO.stream()
                .filter(jugador -> liga == null || liga.equalsIgnoreCase(String.valueOf(jugador.get("liga"))))
                .map(jugador -> Map.of(
                        "nombre", jugador.get("club"),
                        "liga", jugador.get("liga"),
                        "pais", paisPorLiga(String.valueOf(jugador.get("liga"))),
                        "escudo", jugador.get("imagenEscudo")
                ))
                .distinct()
                .toList();
    }

    public List<Map<String, Object>> obtenerJugadoresPorEquipo(String equipo) {
        return JUGADORES_REALES_DEMO.stream()
                .filter(jugador -> equipo == null || equipo.equalsIgnoreCase(String.valueOf(jugador.get("club"))))
                .toList();
    }

    public List<Map<String, Object>> importarJugadoresReales() {
        return JUGADORES_REALES_DEMO;
    }

    public List<CartaFUTDTO> importarCartasReales() {
        return JUGADORES_REALES_DEMO.stream()
                .map(this::mapToCarta)
                .toList();
    }

    public List<Map<String, Object>> obtenerTemporada(String temporada) {
        List<Map<String, Object>> datos = new ArrayList<>();
        Map<String, Object> temporadaDemo = new HashMap<>();
        temporadaDemo.put("temporada", temporada == null ? season : temporada);
        temporadaDemo.put("apiUrl", apiUrl);
        temporadaDemo.put("estado", getEstadoConfiguracion());
        datos.add(temporadaDemo);
        return datos;
    }

    public String obtenerUrlEscudo(Long teamId) {
        return "https://media.api-sports.io/football/teams/" + teamId + ".png";
    }

    public String obtenerUrlFotoJugador(Long playerId) {
        return "https://media.api-sports.io/football/players/" + playerId + ".png";
    }

    public List<CartaFUTDTO> obtenerCartasDemo() {
        // API preparada: si se configura una clave real, aqui se podria llamar a API-Football.
        // Para clase dejamos datos demo, asi la app funciona sin depender de internet.
        return List.of(
                carta("API Especial", "Especial", 91, 88, 90, 86, 92, 45, 76,
                        "https://media.api-sports.io/football/players/276.png",
                        "https://media.api-sports.io/football/teams/541.png",
                        "https://media.api-sports.io/flags/br.svg",
                        "#d946ef", "La Liga", "Real Madrid", "Brasil", "EI", 150000, "Cazador"),
                carta("API Oro", "Oro Rare", 89, 84, 86, 90, 88, 78, 82,
                        "https://media.api-sports.io/football/players/762.png",
                        "https://media.api-sports.io/football/teams/529.png",
                        "https://media.api-sports.io/flags/es.svg",
                        "#facc15", "La Liga", "FC Barcelona", "España", "MC", 95000, "Motor"),
                carta("API Premier", "Oro Rare", 90, 87, 91, 78, 85, 50, 90,
                        "https://media.api-sports.io/football/players/1100.png",
                        "https://media.api-sports.io/football/teams/50.png",
                        "https://media.api-sports.io/flags/no.svg",
                        "#fde68a", "Premier League", "Manchester City", "Noruega", "DC", 140000, "Halcón")
        );
    }

    public String getEstadoConfiguracion() {
        if (!apiEnabled) {
            return "API desactivada. Se usan jugadores reales fijos para modo demo.";
        }
        if (apiKey == null || apiKey.isBlank() || "TU_API_KEY_AQUI".equals(apiKey)) {
            return "API activada pero sin clave real. Configura FOOTBALL_API_KEY.";
        }
        return "API configurada para host " + apiHost + ". Lista para conectar con API-Football.";
    }

    private CartaFUTDTO mapToCarta(Map<String, Object> jugador) {
        CartaFUTDTO dto = new CartaFUTDTO();
        dto.setNombreJugador(String.valueOf(jugador.get("nombre")));
        dto.setTipoCarta(tipoPorMedia((Integer) jugador.get("media")));
        dto.setRareza(rarezaPorMedia((Integer) jugador.get("media")));
        dto.setMedia((Integer) jugador.get("media"));
        dto.setRitmo((Integer) jugador.get("ritmo"));
        dto.setTiro((Integer) jugador.get("tiro"));
        dto.setPase((Integer) jugador.get("pase"));
        dto.setRegate((Integer) jugador.get("regate"));
        dto.setDefensa((Integer) jugador.get("defensa"));
        dto.setFisico((Integer) jugador.get("fisico"));
        dto.setLiga(String.valueOf(jugador.get("liga")));
        dto.setClub(String.valueOf(jugador.get("club")));
        dto.setNacionalidad(String.valueOf(jugador.get("nacionalidad")));
        dto.setPosicion(String.valueOf(jugador.get("posicion")));
        dto.setPrecioMonedas(precioPorMedia(dto.getMedia(), dto.getRareza()));
        dto.setColorCarta(colorPorRareza(dto.getRareza()));
        dto.setEstiloCarta("Motor");
        dto.setImagenJugador(String.valueOf(jugador.get("imagenJugador")));
        dto.setImagenEscudo(String.valueOf(jugador.get("imagenEscudo")));
        dto.setImagenBandera(String.valueOf(jugador.get("imagenBandera")));
        dto.setEnClub(true);
        return dto;
    }

    private List<Map<String, Object>> datosDemo(String tipo, int cantidad) {
        List<Map<String, Object>> datos = new ArrayList<>();
        for (int i = 1; i <= cantidad; i++) {
            Map<String, Object> dato = new HashMap<>();
            dato.put("tipo", tipo);
            dato.put("nombre", tipo + " demo " + i);
            dato.put("temporada", season);
            dato.put("apiPreparada", true);
            datos.add(dato);
        }
        return datos;
    }

    private static Map<String, Object> jugadorReal(String nombre, String club, String liga, String nacionalidad,
                                                   String posicion, Integer edad, Integer media, Integer ritmo,
                                                   Integer tiro, Integer pase, Integer regate, Integer defensa,
                                                   Integer fisico, Integer playerId) {
        return Map.ofEntries(
                Map.entry("nombre", nombre),
                Map.entry("club", club),
                Map.entry("liga", liga),
                Map.entry("nacionalidad", nacionalidad),
                Map.entry("posicion", posicion),
                Map.entry("edad", edad),
                Map.entry("media", media),
                Map.entry("ritmo", ritmo),
                Map.entry("tiro", tiro),
                Map.entry("pase", pase),
                Map.entry("regate", regate),
                Map.entry("defensa", defensa),
                Map.entry("fisico", fisico),
                Map.entry("imagenJugador", "https://media.api-sports.io/football/players/" + playerId + ".png"),
                Map.entry("imagenEscudo", "https://ui-avatars.com/api/?name=" + club.replace(" ", "+") + "&background=0f766e&color=ffffff"),
                Map.entry("imagenBandera", "https://ui-avatars.com/api/?name=" + nacionalidad.substring(0, 2).toUpperCase() + "&background=1d4ed8&color=ffffff")
        );
    }

    private String paisPorLiga(String liga) {
        return switch (liga) {
            case "LaLiga" -> "España";
            case "Premier League" -> "Inglaterra";
            case "Bundesliga" -> "Alemania";
            case "Serie A" -> "Italia";
            default -> "Europa";
        };
    }

    private String tipoPorMedia(Integer media) {
        if (media >= 90) return "Especial";
        if (media >= 75) return "Oro";
        if (media >= 65) return "Plata";
        return "Bronce";
    }

    private String rarezaPorMedia(Integer media) {
        if (media >= 90) return "Especial";
        if (media >= 85) return "Oro raro";
        if (media >= 75) return "Oro comun";
        if (media >= 72) return "Plata rara";
        if (media >= 65) return "Plata comun";
        return media >= 62 ? "Bronce raro" : "Bronce comun";
    }

    private String colorPorRareza(String rareza) {
        if (rareza.contains("Especial")) return "#60a5fa";
        if (rareza.contains("Plata")) return "#cbd5e1";
        if (rareza.contains("Bronce")) return "#b45309";
        return "#facc15";
    }

    private Integer precioPorMedia(Integer media, String rareza) {
        int precio = media * 1000;
        if (rareza.contains("Especial")) precio *= 3;
        if (rareza.contains("raro")) precio += 15000;
        return precio;
    }

    private CartaFUTDTO carta(String tipoCarta, String rareza, Integer media, Integer ritmo,
                              Integer tiro, Integer pase, Integer regate, Integer defensa,
                              Integer fisico, String imagenJugador, String imagenEscudo,
                              String imagenBandera, String colorCarta, String liga, String club,
                              String nacionalidad, String posicion, Integer precio, String estilo) {
        CartaFUTDTO dto = new CartaFUTDTO();
        dto.setTipoCarta(tipoCarta);
        dto.setRareza(rareza);
        dto.setMedia(media);
        dto.setRitmo(ritmo);
        dto.setTiro(tiro);
        dto.setPase(pase);
        dto.setRegate(regate);
        dto.setDefensa(defensa);
        dto.setFisico(fisico);
        dto.setImagenJugador(imagenJugador);
        dto.setImagenEscudo(imagenEscudo);
        dto.setImagenBandera(imagenBandera);
        dto.setColorCarta(colorCarta);
        dto.setLiga(liga);
        dto.setClub(club);
        dto.setNacionalidad(nacionalidad);
        dto.setPosicion(posicion);
        dto.setPrecioMonedas(precio);
        dto.setEstiloCarta(estilo);
        return dto;
    }
}
