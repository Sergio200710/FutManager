# Objetivos

Los objetivos son retos sencillos que dan monedas y experiencia.

## Ejemplos

- Gana 1 partido.
- Gana 3 partidos.
- Crea una plantilla con quimica 80.
- Abre 3 sobres.
- Compra una carta en el mercado.

## Entidad

`Objetivo` guarda descripcion, tipo, progreso, completado, recompensaMonedas y recompensaExperiencia.

## Endpoints

```text
GET /api/objetivos
POST /api/objetivos/{id}/completar
```
