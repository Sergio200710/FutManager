# Plantillas

Las plantillas sirven para montar un once tipo Ultimate Team.

## Entidades

`Plantilla` guarda:

- nombre
- formacion
- mediaTotal
- quimica
- valoracionOfensiva
- valoracionDefensiva
- cartas

`PlantillaCarta` une una plantilla con una carta.
Tambien puede guardar la posicion en la plantilla.

## Reglas

- Maximo 11 cartas.
- No se permite repetir la misma carta.
- La formacion por defecto es 4-3-3.
- La media se calcula con la media de las cartas.
- El ataque usa ritmo, tiro, pase y regate.
- La defensa usa defensa y fisico.
- La quimica sube por club, liga y nacionalidad compartida.

## Endpoints

```text
GET /api/plantillas
GET /api/plantillas/{id}
POST /api/plantillas
PUT /api/plantillas/{id}
DELETE /api/plantillas/{id}
POST /api/plantillas/{plantillaId}/cartas/{cartaId}
DELETE /api/plantillas/{plantillaId}/cartas/{cartaId}
GET /api/plantillas/{id}/resumen
```

## Explicacion para clase

La plantilla no guarda calculos complicados.
Cuando se añade o quita una carta, el servicio recalcula media, ataque, defensa y quimica.
