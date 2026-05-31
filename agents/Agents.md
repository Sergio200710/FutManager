# Agentes de IA - FutManager

Esta carpeta documenta cómo se ha usado la IA durante el desarrollo y revisión del proyecto.

El objetivo no es crear agentes reales complejos, sino dejar claro qué tareas se han pedido a la IA y qué parte del proyecto ha ayudado a revisar o generar.

## Agentes documentados

| Agente | Archivo | Función |
|---|---|---|
| Agente de generación de código | `backend-agent.md` y `frontend-agent.md` | Ayuda a crear código sencillo del backend y frontend |
| Agente de testing | `testing-agent.md` | Ayuda a crear y revisar tests unitarios |
| Agente de documentación | `database-agent.md` y este archivo | Ayuda a explicar la base de datos, DTOs, mapeo, API externa y ejecución |
| Agente de revisión | `backend-agent.md` | Ayuda a encontrar fallos y mejoras del backend |
| Agente de diseño frontend | `frontend-agent.md` | Ayuda a mejorar dashboard, cartas, sobres y responsive |
| Agente de importación de datos | `database-agent.md` | Ayuda a preparar jugadores reales demo y API externa |

## Proyecto revisado

- Nombre: FutManager
- Backend: Spring Boot
- Frontend: React + Vite
- Base de datos: MySQL 8 en Docker
- Puerto MySQL: 3309
- Puerto backend: 8080
- Puerto frontend: 5173
- Tests: JUnit 5 + Mockito

## Entidades principales

- Equipo
- Jugador
- CartaFUT
- Plantilla
- PlantillaCarta
- PartidoSimulado
- Liga
- ClubUsuario
- Sobre
- MercadoCarta
- Objetivo
- EvolucionCarta

## Funcionalidades ampliadas

- Cartas FUT visuales con media, stats, club, liga, nacionalidad, imagenes y precio.
- Importacion preparada desde una API externa de futbol.
- Importador masivo de ligas, equipos, jugadores y cartas.
- Modo demo con muchos datos si no hay API Key.
- Creacion de plantillas con un maximo de 11 cartas.
- Calculo de media total, quimica, ataque y defensa.
- Simulador de partidos con resultado, eventos y resumen.
- Club del usuario con monedas, nivel, experiencia y estadisticas.
- Mis cartas, mercado, sobres, objetivos y evoluciones.
- Buscador avanzado con filtros combinables.
- Animacion propia de apertura de sobres.

## Uso de IA en el desarrollo

La IA se ha usado como apoyo para generar codigo base, revisar errores, proponer pantallas React, documentar endpoints, preparar jugadores reales demo y crear una animacion visual propia para sobres.

## Arquitectura usada

La arquitectura es sencilla y adecuada para 1º DAM:

```text
Controller -> Service -> Repository -> Entity -> MySQL
```

Además se han añadido DTOs y mappers manuales:

```text
Entity -> Mapper -> DTO
DTO -> Mapper -> Entity
```

## Endpoint de tests

El frontend consulta:

```text
GET /api/test-results
```

Este endpoint muestra un resumen sencillo de los tests disponibles. Los tests reales se ejecutan desde terminal con:

```bash
./mvnw test
```
