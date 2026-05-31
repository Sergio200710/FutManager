# Funcionalidades de FutManager

## Gestión básica

La app permite gestionar:

- Ligas
- Equipos
- Jugadores
- Cartas FUT

Cada pantalla permite crear, listar, editar y eliminar datos.

## Jugadores

Cada jugador tiene:

- nombre
- posición
- nacionalidad
- edad
- equipo
- media
- ritmo
- tiro
- pase
- regate
- defensa
- físico
- liga
- foto
- altura
- peso

## Cartas FUT

Las cartas tienen más información visual y de juego:

- tipo de carta
- rareza
- media
- estadísticas
- imágenes
- club
- liga
- nacionalidad
- posición
- precio en monedas

La pantalla de cartas incluye:

- buscador
- filtros por liga, equipo, nacionalidad, posicion, media y rareza
- paginacion
- orden por media, precio, posicion o equipo

## Importador masivo

El proyecto puede generar datos demo tipo Ultimate Team:

- 10 ligas
- 100 equipos
- 1000 jugadores
- 1000 cartas

Endpoint principal:

```text
GET /api/importar/todo
```

## Plantillas

Una plantilla es un once tipo Ultimate Team.

Permite:

- crear plantilla
- añadir cartas
- quitar cartas
- limitar a 11 cartas
- evitar cartas repetidas

La plantilla calcula:

- media total
- química
- ataque
- defensa

## Simulador

Cuando una plantilla tiene 11 cartas, se puede jugar un partido simulado.

El simulador genera:

- rival automático
- goles del usuario
- goles del rival
- ganador
- resumen
- eventos
- mejores jugadores
