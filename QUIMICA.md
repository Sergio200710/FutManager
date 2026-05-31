# Quimica

La quimica mide si las cartas encajan bien entre si.

## Reglas sencillas

- +3 puntos si dos cartas comparten club.
- +2 puntos si dos cartas comparten liga.
- +1 punto si dos cartas comparten nacionalidad.
- La quimica nunca pasa de 100.

## Servicio

La logica esta en `QuimicaService`.

Devuelve total de quimica y una explicacion sencilla.

## Uso

Cuando se añade o quita una carta de una plantilla, `PlantillaService` recalcula media, ataque, defensa, quimica y valoracion general.
