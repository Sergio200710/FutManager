# API Football

## API elegida

La API recomendada para FutManager es **API-Football de API-Sports**.

## Comparacion sencilla

| API | Ventajas | Inconvenientes |
|---|---|---|
| API-Football | Tiene ligas, equipos, jugadores, temporadas, escudos, fotos y estadisticas | Necesita API Key y el plan gratis puede ser limitado |
| Sportmonks | Muy completa y profesional | Puede ser mas cara y amplia para un proyecto de clase |
| football-data.org | Sencilla para competiciones y partidos | Menos enfocada a fotos, escudos y datos tipo FUT |
| OpenLigaDB | Facil de probar | Cobertura mas limitada |

## Por que se elige API-Football

Se elige porque encaja mejor con una app tipo Ultimate Team:

- Permite trabajar con equipos reales.
- Permite trabajar con jugadores reales.
- Tiene ligas y temporadas.
- Tiene imagenes de equipos y jugadores.
- Tiene estadisticas.
- Tiene documentacion clara.

## Configuracion

En `application.properties`:

```properties
football.api.url=https://v3.football.api-sports.io
football.api.host=v3.football.api-sports.io
football.api.key=${FOOTBALL_API_KEY:TU_API_KEY_AQUI}
football.api.season=2025
```

Para usar una clave real:

```bash
export FOOTBALL_API_KEY=tu_clave_real
```

No se debe subir una clave real al repositorio.

## Servicio preparado

Archivo:

```text
src/main/java/com/futmanager/demofut/service/FootballApiService.java
```

Metodos preparados:

- `obtenerLigas()`
- `obtenerEquipos()`
- `obtenerJugadores()`
- `obtenerTemporada()`
- `obtenerUrlEscudo()`
- `obtenerUrlFotoJugador()`

Ahora mismo devuelve datos demo si no hay clave real, para que el proyecto funcione siempre en clase.
