# Agente de generación y revisión de backend

## Función

Este agente ayudó a crear y revisar el backend de FutManager con Spring Boot.

La idea fue mantener un código sencillo, parecido al que puede explicar un alumno de 1º DAM.

## Partes revisadas

- Entidades JPA
- Repositories
- Services
- Controllers
- Validaciones
- DTOs
- Mappers manuales
- Endpoints REST

## Entidades actuales

### Equipo

Campos:

- id
- nombre
- liga
- pais
- jugadores

Relación:

```text
Equipo 1 -> N Jugador
```

### Jugador

Campos:

- id
- nombre
- posicion
- nacionalidad
- edad
- media
- ritmo
- tiro
- pase
- regate
- defensa
- fisico
- equipo
- cartaFut

Relaciones:

```text
Jugador N -> 1 Equipo
Jugador 1 -> 1 CartaFUT
```

### CartaFUT

Campos:

- id
- tipoCarta
- rareza
- media
- ritmo
- tiro
- pase
- regate
- defensa
- fisico
- imagenJugador
- imagenEscudo
- imagenBandera
- colorCarta
- liga
- club
- nacionalidad
- posicion
- precioMonedas
- estiloCarta
- jugador

Relación:

```text
CartaFUT 1 -> 1 Jugador
```

### Plantilla

Campos principales:

- id
- nombre
- mediaTotal
- quimica
- valoracionOfensiva
- valoracionDefensiva
- cartas

### PlantillaCarta

Tabla intermedia sencilla para guardar que cartas forman parte de una plantilla.

### PartidoSimulado

Guarda el resultado de un partido simulado:

- plantilla
- equipoRival
- golesUsuario
- golesRival
- ganador
- resumen
- mejoresJugadores
- eventos

## Mejoras aplicadas

- Se movió lógica de negocio desde `JugadorController` hacia `JugadorService`.
- Se añadieron validaciones básicas a `CartaFUT`.
- Se unificaron los `DELETE` para devolver `204 No Content`.
- Se añadieron DTOs reales:
  - `JugadorDTO`
  - `EquipoDTO`
  - `CartaFUTDTO`
- Se añadieron mappers manuales:
  - `JugadorMapper`
  - `EquipoMapper`
  - `CartaFUTMapper`
  - `PlantillaMapper`
  - `PartidoSimuladoMapper`
- Se añadió la funcionalidad de plantillas.
- Se añadió el simulador de partidos.
- Se dejó preparada la conexion con una API externa de futbol.

## Decisiones sencillas

- No se usa JWT.
- No se usan microservicios.
- No se usa Clean Architecture.
- No se usa MapStruct.
- El mapeo es manual para que sea fácil de entender.
