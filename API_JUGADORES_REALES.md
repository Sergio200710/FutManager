# API de jugadores reales

## API recomendada

Para este proyecto se recomienda **API-Football de API-Sports**.

Motivos:

- Tiene ligas, equipos, jugadores, fotos, escudos y temporadas.
- Encaja bien con una app educativa de Spring Boot.
- Ya usa URLs de imagen faciles de guardar en base de datos.
- Permite dejar preparada la integracion sin poner claves reales.

Alternativas:

- Sportmonks: muy completa, pero puede ser mas grande para 1º DAM.
- football-data.org: sencilla para competiciones y equipos, pero menos orientada a cartas con imagenes de jugadores.

## Configuracion

```properties
football.api.url=https://v3.football.api-sports.io
football.api.host=v3.football.api-sports.io
football.api.key=${FOOTBALL_API_KEY:TU_API_KEY_AQUI}
football.api.enabled=false
football.api.season=2025
```

## Modo demo real

Si `football.api.enabled=false`, FutManager usa una lista fija de jugadores reales conocidos.
No se inventan nombres.

Ejemplos:

- Kylian Mbappe
- Vinicius Jr
- Jude Bellingham
- Erling Haaland
- Lamine Yamal
- Pedri
- Rodri
- Mohamed Salah
- Jamal Musiala
- Bukayo Saka

## Endpoints

```text
GET /api/importar/equipos-reales
GET /api/importar/jugadores-reales
GET /api/importar/cartas-reales
GET /api/importar/todo-real
```

El importador evita duplicados y no borra datos anteriores.
