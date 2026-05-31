# Agente de documentación de base de datos

## Función

Este agente ayudó a documentar la base de datos MySQL y las relaciones entre entidades.

## Configuración real

La base de datos se levanta con Docker Compose.

Datos principales:

| Dato | Valor |
|---|---|
| Motor | MySQL 8 |
| Base de datos | futmanager |
| Puerto externo | 3309 |
| Usuario | sergio |
| Contraseña | sergio123 |
| phpMyAdmin | http://localhost:8091 |

## Tablas principales

### liga

Campos principales:

- id
- nombre
- pais
- temporada
- logo

### equipo

Campos principales:

- id
- nombre
- liga
- pais
- escudo
- estadio
- liga_id

### jugador

Campos principales:

- id
- nombre
- posicion
- apellido
- nacionalidad
- edad
- media
- ritmo
- tiro
- pase
- regate
- defensa
- fisico
- equipo_id
- liga
- foto
- altura
- peso

### cartafut

Campos principales:

- id
- tipo_carta
- rareza
- media
- ritmo
- tiro
- pase
- regate
- defensa
- fisico
- imagen_jugador
- imagen_escudo
- imagen_bandera
- color_carta
- liga
- club
- nacionalidad
- posicion
- precio_monedas
- estilo_carta
- jugador_id

### plantilla

Campos principales:

- id
- nombre
- media_total
- quimica
- valoracion_ofensiva
- valoracion_defensiva

### plantilla_carta

Campos principales:

- id
- plantilla_id
- carta_id

### partido_simulado

Campos principales:

- id
- plantilla_id
- equipo_rival
- goles_usuario
- goles_rival
- ganador
- resumen
- mejores_jugadores
- eventos

## Relaciones

```text
equipo (1) -> (N) jugador
liga (1) -> (N) equipo
jugador (1) -> (1) carta_fut
plantilla (1) -> (N) plantilla_carta
carta_fut (1) -> (N) plantilla_carta
plantilla (1) -> (N) partido_simulado
```

## Creación de tablas

Hibernate actualiza las tablas automáticamente con:

```text
spring.jpa.hibernate.ddl-auto=update
```

Esto es cómodo para un proyecto de clase, aunque en proyectos grandes se usarían migraciones.
