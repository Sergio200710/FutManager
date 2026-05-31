# Importador de datos

## Objetivo

El importador sirve para llenar FutManager con muchos datos, parecido a un Ultimate Team sencillo.

Puede trabajar de dos formas:

- API real si se configura una clave.
- Modo demo si no hay clave.

## Endpoints

| Endpoint | Que hace |
|---|---|
| `GET /api/importar/ligas` | Crea 10 ligas |
| `GET /api/importar/equipos` | Crea hasta 100 equipos |
| `GET /api/importar/jugadores` | Crea hasta 1000 jugadores |
| `GET /api/importar/cartas` | Genera cartas FUT para los jugadores |
| `GET /api/importar/todo` | Ejecuta todo en orden |

## Orden correcto

```text
Ligas -> Equipos -> Jugadores -> Cartas FUT
```

Este orden es importante porque:

- Un equipo necesita liga.
- Un jugador necesita equipo.
- Una carta necesita jugador.

## Modo demo

La clase `DemoDataLoader` ejecuta el modo demo solo si la base de datos esta vacia.

Datos generados:

- 10 ligas.
- 100 equipos.
- 1000 jugadores.
- 1000 cartas.

## Archivos importantes

- `ImportadorDatosController.java`
- `ImportadorDatosService.java`
- `DemoDataLoader.java`
- `CartaFUTGeneratorService.java`
- `FootballApiService.java`
