# API de cartas y jugadores

## API elegida

La integración queda preparada para API-Football/API-Sports.

Esta API permite obtener datos de:

- jugadores
- equipos
- ligas
- escudos
- fotos de jugadores
- países y banderas
- temporadas
- estadisticas

## Configuración

No se debe poner una clave real en el código.

Se configura en `application.properties`:

```properties
football.api.key=TU_API_KEY_AQUI
football.api.host=v3.football.api-sports.io
football.api.url=https://v3.football.api-sports.io
football.api.season=2025
```

## Servicio creado

Archivo:

```text
src/main/java/com/futmanager/demofut/service/FootballApiService.java
```

Este servicio deja preparada la conexión y tiene metodos para ligas, equipos, jugadores, temporada, escudos y fotos.

Como no siempre habrá clave real ni internet en clase, devuelve datos demo para poder enseñar la funcionalidad.

## Endpoint

```text
GET /api/cartas/importar-api
```

Este endpoint importa cartas preparadas y las guarda en la base de datos.

Tambien existe un importador masivo:

```text
GET /api/importar/todo
```

## Por qué se hace así

Es una solución sencilla:

- No usa claves reales.
- No rompe la app si no hay internet.
- Permite explicar cómo se conectaría una API externa.
- Mantiene el proyecto fácil para 1º DAM.
