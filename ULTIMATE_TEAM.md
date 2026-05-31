# Modo Ultimate Team

FutManager incluye un modo Ultimate Team sencillo para clase.

## Cartas FUT

Cada carta guarda:

- nombreJugador
- media
- posicion
- ritmo, tiro, pase, regate, defensa y fisico
- club, liga y nacionalidad
- rareza y tipoCarta
- precioMonedas
- imagenJugador, imagenEscudo e imagenBandera

Rarezas usadas:

- Bronce comun
- Bronce raro
- Plata comun
- Plata rara
- Oro comun
- Oro raro
- Especial
- Heroe
- Icono

## Plantillas

Una plantilla tiene nombre y una lista de cartas.

Reglas:

- maximo 11 cartas
- no permite cartas repetidas
- calcula media total
- calcula ataque
- calcula defensa
- calcula quimica
- usa formacion 4-3-3 por defecto

Endpoints principales:

```text
GET /api/plantillas
POST /api/plantillas
GET /api/plantillas/{id}
POST /api/plantillas/{plantillaId}/cartas/{cartaId}
DELETE /api/plantillas/{plantillaId}/cartas/{cartaId}
```

## Quimica

La quimica suma puntos por relaciones entre jugadores:

- mismo club
- misma liga
- misma nacionalidad

El resultado se limita de 0 a 100.

## Club del usuario

El club guarda:

- nombreClub
- monedas
- nivel
- experiencia

Las monedas se usan para comprar cartas y abrir sobres.
La experiencia permite subir el nivel del club.

## Objetivos y evoluciones

Los objetivos son retos sencillos con recompensa en monedas.
Las evoluciones permiten mejorar una carta sumando media, ritmo, tiro y pase.
