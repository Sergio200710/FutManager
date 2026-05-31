# FutManager

FutManager es una app Full Stack sencilla para gestionar equipos, jugadores, cartas FUT, plantillas tipo Ultimate Team y partidos simulados.

La arquitectura se mantiene simple y defendible para 1º DAM:

```text
Controller -> Service -> Repository -> Entity
DTO -> Mapper manual
```

No usa microservicios, JWT, CQRS ni arquitectura avanzada.

## Tecnologias

| Parte | Tecnologia |
|---|---|
| Backend | Spring Boot |
| Lenguaje | Java 21 |
| Base de datos | MySQL 8 |
| ORM | Spring Data JPA |
| Frontend | React + Vite |
| HTTP frontend | Axios |
| Tests | JUnit 5 + Mockito |
| Contenedores | Docker Compose |

## Base de datos

```bash
docker compose up -d
```

Datos de MySQL:

| Dato | Valor |
|---|---|
| Host | localhost |
| Puerto | 3308 |
| Base de datos | futmanager_db |
| Usuario | root |
| Contrasena | root1234 |

También se incluye **phpMyAdmin** para gestionar la base de datos visualmente en:
`http://localhost:8098`

## Ejecutar backend

```bash
./mvnw spring-boot:run
```

Backend:

```text
http://localhost:8080
```

## Ejecutar frontend

```bash
cd futmanager-front
npm install
npm run dev
```

Frontend:

```text
http://localhost:5173
```

## Tests

```bash
./mvnw test
```

Panel visual:

```text
GET /api/test-results
```

## API recomendada

La API elegida para preparar la integracion es **API-Football de API-Sports**.

Se ha elegido porque tiene jugadores, equipos, ligas, temporadas, escudos, fotos de jugadores, estadisticas y buena documentacion. Para un proyecto de clase es buena porque permite explicar una integracion real, pero la app no depende de tener clave para funcionar.

Configuracion:

```properties
football.api.url=https://v3.football.api-sports.io
football.api.host=v3.football.api-sports.io
football.api.key=${FOOTBALL_API_KEY:TU_API_KEY_AQUI}
football.api.enabled=false
football.api.season=2025
```

No se guarda ninguna clave real en el codigo.
Si `football.api.enabled=false`, el proyecto usa una lista fija de jugadores reales conocidos.

## Jerarquía de Datos Reales

El proyecto utiliza exclusivamente jugadores, equipos y ligas **reales**. No hay datos inventados ni nombres genéricos.

La estructura relacional principal es la siguiente:
- **Liga**: Contiene varios equipos (ej. LaLiga, Premier League).
- **Equipo**: Pertenece a una liga y contiene varios jugadores (ej. Real Madrid, Manchester City).
- **Jugador**: Es una persona real que juega en un equipo (ej. Kylian Mbappé, Erling Haaland).
- **CartaFUT**: Es el objeto coleccionable en el juego (con stats y precio), asociado directamente a un Jugador.

## Inyección Automática de Datos

Al arrancar el backend (`./mvnw spring-boot:run`), la clase `DemoDataLoader` verificará si la base de datos está vacía. Si es así, importará automáticamente una jerarquía completa de datos reales, incluyendo equipos como el FC Barcelona, Bayern Munich, Inter Miami y sus respectivos jugadores (Yamal, Kane, Messi, etc.), así como la generación de sus cartas FUT asociadas.

## Importadores

| Metodo | URL | Funcion |
|---|---|---|
| GET | /api/importar/ligas | Crea o importa ligas |
| GET | /api/importar/equipos | Crea o importa equipos |
| GET | /api/importar/jugadores | Crea o importa jugadores |
| GET | /api/importar/cartas | Genera cartas FUT |
| GET | /api/importar/todo | Ejecuta todo en orden |

Orden de `/api/importar/todo`:

1. Ligas
2. Equipos
3. Jugadores
4. Cartas FUT

## Filtros y paginacion

Cartas paginadas:

```text
GET /api/cartas?page=0&size=20
```

Filtros de cartas:

```text
GET /api/cartas?liga=LaLiga&posicion=DC&rareza=Oro raro&mediaMin=85
```

Jugadores paginados:

```text
GET /api/jugadores?page=0&size=20
```

Orden:

```text
GET /api/cartas?page=0&size=20&sortBy=media&direction=desc
```

Buscador avanzado de cartas:

```text
GET /api/cartas/buscar?nombre=mbappe&liga=LaLiga&mediaMin=85&page=0&size=20
```

Filtros combinables:

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

## Funcionalidades principales

- CRUD de equipos.
- CRUD de jugadores.
- CRUD de cartas FUT.
- Cartas visuales con imagen, media, posicion, stats, club, liga, nacionalidad y rareza.
- Buscador y filtros de cartas.
- Importador de jugadores reales o demo real sin nombres inventados.
- Animacion visual propia al abrir sobres.
- Paginacion para manejar muchos datos.
- Crear plantillas con hasta 11 cartas.
- Evitar cartas repetidas en una plantilla.
- Calcular media, quimica, ataque y defensa.
- Simular partidos con eventos y resumen.
- Abrir sobres de bronce, plata, oro y especial.
- Mercado basico para comprar y vender cartas con monedas.
- Monedero demo con monedas iniciales y premios por partido.
- Club del usuario con nombre, monedas, nivel y experiencia.
- Objetivos con recompensas.
- Evoluciones sencillas para mejorar cartas.
- Importador masivo preparado para API real o datos demo.

## Modo Ultimate Team

El modo Ultimate Team usa cartas FUT para crear una plantilla de hasta 11 titulares.
Cada carta tiene nombre, media, posicion, stats, club, liga, nacionalidad, rareza,
precio e imagenes.

La plantilla calcula automaticamente media total, quimica, ataque y defensa.
La quimica sube cuando las cartas comparten club, liga o nacionalidad.

## Crear plantilla

Frontend:

```text
http://localhost:5173/plantillas
```

API:

```text
POST /api/plantillas
POST /api/plantillas/{plantillaId}/cartas/{cartaId}
DELETE /api/plantillas/{plantillaId}/cartas/{cartaId}
```

## Jugar partido

Una plantilla puede jugar cuando tiene 11 cartas:

```text
POST /api/simulador/plantilla/{plantillaId}
```

El resultado usa media, ataque, defensa, quimica y azar. Al jugar se ganan monedas.

## Sobres

Tipos disponibles:

- bronce
- plata
- oro
- especial

Endpoint:

```text
POST /api/sobres/abrir/{tipoSobre}
```

## Mercado y monedas

El usuario demo empieza con 100000 monedas.

```text
GET /api/monedero
GET /api/club
PUT /api/club
GET /api/cartas/mis-cartas
GET /api/club/cartas
POST /api/club/cartas/{cartaId}/vender
PUT /api/club/cartas/{cartaId}/transferible
GET /api/mercado/cartas
POST /api/mercado/comprar/{cartaId}
POST /api/mercado/vender/{cartaId}
```

## Importar jugadores reales

```text
GET /api/importar/equipos-reales
GET /api/importar/jugadores-reales
GET /api/importar/cartas-reales
GET /api/importar/todo-real
```

Con API desactivada, estos endpoints usan jugadores reales fijos como Mbappe,
Vinicius Jr, Bellingham, Haaland, Lamine Yamal, Pedri, Rodri, Salah y otros.

## Objetivos y evoluciones

```text
GET /api/objetivos
POST /api/objetivos/{id}/completar
GET /api/evoluciones
POST /api/evoluciones/carta/{cartaId}
```

## Capturas recomendadas para PDF

- Dashboard con nombre del club, monedas y nivel.
- Grid de cartas con filtros.
- Plantilla con 11 cartas y quimica.
- Resultado de un partido simulado.
- Pantalla de sobres abierta.
- Mercado con compra o venta.
- Objetivos completados.
- Evolucion de una carta.

## Video recomendado

En el video explica este flujo:

1. Arrancar backend y frontend.
2. Enseñar cartas y filtros.
3. Crear plantilla y añadir cartas.
4. Jugar partido y ganar monedas.
5. Abrir un sobre.
6. Comprar una carta en mercado.
7. Completar un objetivo.
8. Evolucionar una carta.

## Documentacion adicional

- `ULTIMATE_TEAM.md`
- `API_FOOTBALL.md`
- `API_JUGADORES_REALES.md`
- `IMPORTADOR_DATOS.md`
- `CARTAS_FUT.md`
- `FILTROS_CARTAS.md`
- `ANIMACION_SOBRES.md`
- `PLANTILLAS.md`
- `QUIMICA.md`
- `FUNCIONALIDADES.md`
- `API_CARTAS.md`
- `SIMULADOR.md`
- `SOBRES.md`
- `MERCADO.md`
- `OBJETIVOS.md`
- `EVOLUCIONES.md`
- `DOCUMENTACION_ENTREGA.md`
- `DTO.md`
- `MAPEO.md`
# FutManager
