# Mercado

El mercado es una version sencilla para comprar y vender cartas con monedas.
No hay subastas ni pujas, solo precio fijo.

## Monedas

El usuario demo empieza con 100000 monedas.

Se gastan monedas al:

- comprar cartas
- abrir sobres

Se ganan monedas al:

- vender cartas
- jugar partidos simulados

## Endpoints

```text
GET /api/monedero
GET /api/mercado/cartas
POST /api/mercado/comprar/{cartaId}
POST /api/mercado/vender/{cartaId}
```

## Filtros

El mercado permite filtrar por:

- nombre
- posicion
- mediaMin
- liga
- club
- rareza

Ejemplo:

```text
GET /api/mercado/cartas?nombre=vinicius&posicion=EI&mediaMin=85
```
