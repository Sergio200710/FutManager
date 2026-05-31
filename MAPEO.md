# Mapeo de datos en FutManager

## Qué es el mapeo de datos

El mapeo de datos consiste en convertir un objeto de un tipo a otro.

En este proyecto se convierte entre:

```text
Entity <-> DTO
```

Ejemplo:

```text
Jugador -> JugadorDTO
JugadorDTO -> Jugador
```

## Por qué hace falta mapear

Las entidades JPA están pensadas para trabajar con la base de datos.

Los DTOs están pensados para enviar datos al frontend.

Por eso se necesita una clase intermedia que copie los datos necesarios.

## Mapeo usado

No se usa MapStruct ni ModelMapper.

Se usa mapeo manual porque es más fácil de entender para 1º DAM.

Archivos creados:

```text
src/main/java/com/futmanager/demofut/mapper/JugadorMapper.java
src/main/java/com/futmanager/demofut/mapper/EquipoMapper.java
src/main/java/com/futmanager/demofut/mapper/CartaFUTMapper.java
src/main/java/com/futmanager/demofut/mapper/PlantillaMapper.java
src/main/java/com/futmanager/demofut/mapper/PartidoSimuladoMapper.java
```

## Ejemplo de mapeo de Jugador a JugadorDTO

```java
public static JugadorDTO entityToDto(Jugador jugador) {
    Long equipoId = jugador.getEquipo() == null ? null : jugador.getEquipo().getId();
    String equipoNombre = jugador.getEquipo() == null ? null : jugador.getEquipo().getNombre();

    return new JugadorDTO(
        jugador.getId(),
        jugador.getNombre(),
        jugador.getPosicion(),
        jugador.getNacionalidad(),
        jugador.getEdad(),
        jugador.getMedia(),
        jugador.getRitmo(),
        jugador.getTiro(),
        jugador.getPase(),
        jugador.getRegate(),
        jugador.getDefensa(),
        jugador.getFisico(),
        equipoId,
        equipoNombre
    );
}
```

## Ejemplo de mapeo de JugadorDTO a Jugador

```java
public static Jugador dtoToEntity(JugadorDTO dto) {
    Jugador jugador = new Jugador();
    jugador.setId(dto.getId());
    jugador.setNombre(dto.getNombre());
    jugador.setPosicion(dto.getPosicion());
    jugador.setMedia(dto.getMedia());
    jugador.setRitmo(dto.getRitmo());
    jugador.setTiro(dto.getTiro());
    jugador.setPase(dto.getPase());
    jugador.setDefensa(dto.getDefensa());
    jugador.setFisico(dto.getFisico());
    return jugador;
}
```

El equipo se asigna en el service, porque hay que buscarlo en la base de datos con su `id`.

## Diagrama sencillo

```text
              LISTAR JUGADORES

MySQL
  |
Repository
  |
Entity Jugador
  |
JugadorMapper.entityToDto()
  |
JugadorDTO
  |
Controller
  |
React
```

```text
              CREAR JUGADOR

React
  |
JugadorDTO
  |
Controller
  |
Service
  |
JugadorMapper.dtoToEntity()
  |
Entity Jugador
  |
Repository
  |
MySQL
```

## Dónde se usa

Los controllers trabajan con DTOs.

Los services se encargan de convertir y guardar.

Los repositories siguen trabajando con entidades JPA.

## Ventaja principal

El código sigue siendo sencillo, pero la API queda más limpia y más fácil de mantener.
