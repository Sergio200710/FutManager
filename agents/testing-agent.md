# Agente de testing

## Función

Este agente ayudó a crear y revisar los tests unitarios del backend.

## Herramientas

- JUnit 5
- Mockito
- Jakarta Validation

## Archivo principal de tests

```text
src/test/java/com/futmanager/demofut/JugadorServiceTest.java
src/test/java/com/futmanager/demofut/UltimateTeamServiceTest.java
```

## Tests disponibles

- `testGuardarJugadorCorrectamente`
- `testBuscarJugadorPorId`
- `testActualizarMediaDeJugador`
- `testEliminarJugador`
- `testFiltrarPorPosicion`
- `testFiltrarPorMediaMinima`
- `testRechazarNombreVacio`
- `testRechazarMediaFueraDeRango`
- `testCrearEquipo`
- `testComprobarRelacionEquipoJugadores`
- `testCrearCarta`
- `testCrearPlantilla`
- `testAnadirCartaAPlantilla`
- `testEvitarCartaRepetida`
- `testNoPermitirPartidoConPlantillaIncompleta`
- `testSimularPartidoConPlantillaValida`

## Corrección aplicada

Los tests fallaban por un problema de Mockito con Java 21.

Se añadió esta configuración:

```text
src/test/resources/mockito-extensions/org.mockito.plugins.MockMaker
```

Contenido:

```text
mock-maker-subclass
```

Con esto los tests pueden ejecutarse correctamente con:

```bash
./mvnw test
```

## Endpoint de tests

El endpoint:

```text
GET /api/test-results
```

no ejecuta JUnit desde producción. Solo muestra al frontend un resumen sencillo de los tests para que el usuario pueda verlo en pantalla.
