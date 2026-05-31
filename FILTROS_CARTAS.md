# Filtros de cartas

El endpoint avanzado es:

```text
GET /api/cartas/buscar
```

Parametros opcionales:

- nombre
- club
- liga
- nacionalidad
- posicion
- rareza
- mediaMin
- mediaMax
- precioMin
- precioMax
- page
- size

Ejemplo:

```text
GET /api/cartas/buscar?nombre=mbappe&liga=LaLiga&mediaMin=85&page=0&size=20
```

Reglas:

- Los filtros se pueden combinar.
- Si un filtro va vacio, se ignora.
- El nombre ignora mayusculas y minusculas.
- El nombre puede ser parcial.
- La respuesta es paginada.

En React, la pantalla `Cartas` usa este endpoint para mostrar un grid visual.
