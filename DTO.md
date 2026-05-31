# DTOs en FutManager

## Qué es un DTO

Un DTO significa `Data Transfer Object`.

En español se puede explicar como un objeto que se usa para enviar datos entre el backend y el frontend.

No es una tabla de la base de datos. No lleva `@Entity`.

Sirve para no devolver directamente las entidades JPA al frontend.

## Por qué se usan DTOs en este proyecto

Antes el backend podía devolver entidades como `Jugador`, `Equipo` o `CartaFUT` directamente.

Eso funciona en proyectos pequeños, pero puede traer problemas:

- Se envían más datos de los necesarios.
- Las relaciones JPA pueden provocar bucles en JSON.
- El frontend depende demasiado de cómo está hecha la base de datos.
- Cambiar una entidad puede romper la API.

Con DTOs se devuelve solo lo necesario.

## DTOs implementados

Se han creado estos DTOs:

```text
src/main/java/com/futmanager/demofut/dto/JugadorDTO.java
src/main/java/com/futmanager/demofut/dto/EquipoDTO.java
src/main/java/com/futmanager/demofut/dto/CartaFUTDTO.java
```

## JugadorDTO

`JugadorDTO` contiene los datos que necesita el frontend para mostrar y guardar jugadores.

Campos principales:

- id
- nombre
- posicion
- nacionalidad
- edad
- media
- ritmo
- tiro
- pase
- regate
- defensa
- fisico
- equipoId
- equipoNombre

Ejemplo:

```java
public class JugadorDTO {
    private Long id;
    private String nombre;
    private String posicion;
    private Integer media;
    private Integer ritmo;
    private Integer tiro;
    private Integer pase;
    private Integer defensa;
    private Integer fisico;
    private Long equipoId;
    private String equipoNombre;
}
```

## EquipoDTO

`EquipoDTO` contiene:

- id
- nombre
- liga
- pais
- numeroJugadores

El campo `numeroJugadores` evita tener que enviar toda la lista de jugadores al frontend.

## CartaFUTDTO

`CartaFUTDTO` contiene:

- id
- tipoCarta
- rareza
- media
- ritmo
- tiro
- pase
- regate
- defensa
- fisico
- imagenJugador
- imagenEscudo
- imagenBandera
- colorCarta
- liga
- club
- nacionalidad
- posicion
- precioMonedas
- estiloCarta
- jugadorId
- jugadorNombre
- jugadorPosicion

Tambien se han creado DTOs para las nuevas funcionalidades:

- `PlantillaDTO`
- `PartidoSimuladoDTO`

Así el frontend puede mostrar el nombre y posición del jugador sin recibir todo el objeto `Jugador`.

## Validaciones

Los DTOs tienen validaciones sencillas:

- `@NotBlank`
- `@NotNull`
- `@Min`
- `@Max`
- `@Positive`

Ejemplo:

```java
@NotBlank(message = "La rareza no puede estar vacía")
private String rareza;

@NotNull(message = "La valoración no puede ser nula")
@Min(value = 1)
@Max(value = 99)
private Integer valoracion;
```

## Flujo al listar datos

```text
MySQL -> Entity -> Mapper -> DTO -> Controller -> Frontend
```

## Flujo al crear datos

```text
Frontend -> DTO -> Controller -> Service -> Entity -> Repository -> MySQL
```


