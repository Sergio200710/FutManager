# Cartas FUT

## Que es una carta FUT

Una carta FUT representa a un jugador dentro del modo Ultimate Team de FutManager.

Cada carta tiene:

- Jugador.
- Media.
- Posicion.
- Ritmo.
- Tiro.
- Pase.
- Regate.
- Defensa.
- Fisico.
- Rareza.
- Tipo de carta.
- Precio en monedas.
- Imagen del jugador.
- Escudo del club.
- Bandera del pais.
- Liga.
- Club.

## Generador de cartas

Archivo:

```text
src/main/java/com/futmanager/demofut/service/CartaFUTGeneratorService.java
```

El generador crea cartas automaticamente desde un jugador.

## Reglas sencillas

La media siempre queda entre 55 y 99.

Rarezas:

| Media | Rareza |
|---|---|
| 55-64 | Bronce comun o Bronce raro |
| 65-74 | Plata comun o Plata rara |
| 75-84 | Oro comun |
| 85-89 | Oro raro |
| 90-99 | Especial, Icono o TOTS |

Stats por posicion:

- Portero: ritmo bajo, tiro bajo, defensa alta.
- Defensa: defensa alta y fisico alto.
- Centrocampista: pase alto y regate medio.
- Delantero: ritmo, tiro y regate altos.

## Frontend

La pantalla `Cartas` tiene:

- Grid visual de cartas.
- Buscador.
- Filtro por liga.
- Filtro por equipo.
- Filtro por nacionalidad.
- Filtro por posicion.
- Filtro por media minima.
- Filtro por rareza.
- Paginacion.
- Orden por media, precio, posicion o equipo.
