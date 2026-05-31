# Simulador de partidos

## Qué hace

El simulador permite jugar un partido usando una plantilla de 11 cartas.

## Reglas

Para jugar:

- La plantilla debe existir.
- Debe tener exactamente 11 cartas.
- No se permite jugar con plantillas incompletas.

## Datos usados

El resultado se calcula con:

- media total
- valoración ofensiva
- valoración defensiva
- química
- factor aleatorio pequeño

## Qué genera

El partido simulado guarda:

- plantilla
- rival
- goles del usuario
- goles del rival
- ganador
- resumen
- mejores jugadores
- MVP
- eventos del partido
- fecha
- monedas ganadas por jugar

## Endpoint principal

```text
POST /api/simulador/plantilla/{plantillaId}
```

## Historial

```text
GET /api/partidos
GET /api/partidos/{id}
```

## Ejemplo de eventos

```text
Minuto 12: Primera ocasion clara de Mi plantilla.
Minuto 24: Gol de tu delantero.
Minuto 58: Gol del rival.
Minuto 80: Gol decisivo de tu plantilla.
```

## Explicación para clase

El simulador no intenta ser realista al 100%.

Su objetivo es enseñar cómo el backend puede usar datos guardados en MySQL para calcular un resultado y devolverlo al frontend.
