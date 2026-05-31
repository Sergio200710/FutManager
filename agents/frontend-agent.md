# Agente de generación de frontend

## Función

Este agente ayudó a crear y revisar el frontend de FutManager con React.

## Estructura principal

```text
futmanager-front/src
├── App.jsx
├── services/api.js
└── pages
    ├── Home.jsx
    ├── Jugadores.jsx
    ├── Equipos.jsx
    ├── Cartas.jsx
    ├── Plantillas.jsx
    ├── Simulador.jsx
    └── Tests.jsx
```

## Funcionalidades

- Listar jugadores
- Crear jugadores
- Editar jugadores
- Eliminar jugadores
- Filtrar jugadores por posición y media mínima
- Listar equipos
- Crear equipos
- Editar equipos
- Eliminar equipos
- Listar cartas FUT
- Crear cartas FUT
- Editar cartas FUT
- Eliminar cartas FUT
- Importar cartas desde la API preparada
- Ver cartas en formato visual tipo FUT
- Crear plantillas
- Añadir cartas a una plantilla
- Quitar cartas de una plantilla
- Ver media, quimica, ataque y defensa de una plantilla
- Simular partidos con una plantilla de 11 cartas
- Ver eventos y resumen del partido
- Mostrar un panel de tests

## Comunicación con el backend

Las llamadas HTTP están centralizadas en:

```text
futmanager-front/src/services/api.js
```

El frontend llama al backend en:

```text
http://localhost:8080
```

## Revisión aplicada

Se eliminaron imports innecesarios de `React` para que `npm run lint` funcione correctamente.

El funcionamiento visual se mantiene igual.
