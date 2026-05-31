# INFORME DE AUDITORÍA Y CAPTURAS PARA DOCUMENTACIÓN

A continuación, se detalla la lista exacta de capturas que debes realizar para demostrar todos los requisitos del proyecto FutManager, relacionando el código real, las rutas y las funcionalidades.

---

## 1. BACKEND

### REQUISITO: Spring Boot y Configuración

#### Archivo
Ruta completa:
```text
/home/sergio/demofut/src/main/java/com/futmanager/demofut/DemofutApplication.java
```
#### Clase
`DemofutApplication`
#### Captura obligatoria
El archivo principal con la anotación `@SpringBootApplication` y el método `main`.
#### Importancia
Demuestra que el proyecto base está construido y ejecutándose sobre el framework Spring Boot tal como se pide en los requisitos.
#### Texto para el PDF
Se ha empleado el framework Spring Boot para el desarrollo del backend, utilizando su clase principal de autoconfiguración para levantar el contexto de la aplicación y el servidor Tomcat integrado.

---

### REQUISITO: Base de Datos MySQL

#### Archivo
Ruta completa:
```text
/home/sergio/demofut/docker-compose.yml
```
#### Captura obligatoria
El bloque `db` donde se define la imagen `mysql:8.0`, el puerto `3309:3306` y las credenciales.
#### Importancia
Evidencia que la base de datos se despliega usando MySQL mediante Docker Compose, garantizando persistencia y entornos reproducibles.
#### Texto para el PDF
La persistencia de datos está implementada sobre un motor MySQL en su versión 8.0. Para facilitar el despliegue local y la evaluación, se ha contenerizado el servicio a través de Docker Compose, exponiendo el puerto 3309.

---

### REQUISITO: Entidad Jugador

#### Archivo
Ruta completa:
```text
/home/sergio/demofut/src/main/java/com/futmanager/demofut/entity/Jugador.java
```
#### Clase
`Jugador`
#### Captura obligatoria
Las anotaciones `@Entity`, `@Table(name = "jugadores")` y las propiedades (id, nombre, media, etc.) junto con la relación `@ManyToOne` con Equipo.
#### Importancia
Demuestra el uso de JPA/Hibernate para el mapeo objeto-relacional de la entidad central del proyecto.
#### Texto para el PDF
Para la gestión de los futbolistas se ha implementado la entidad JPA `Jugador`. Esta clase mapea directamente la tabla `jugadores` de la base de datos y establece las relaciones necesarias de cardinalidad N:1 con su equipo correspondiente.

---

### REQUISITO: Entidad Equipo

#### Archivo
Ruta completa:
```text
/home/sergio/demofut/src/main/java/com/futmanager/demofut/entity/Equipo.java
```
#### Clase
`Equipo`
#### Captura obligatoria
Las anotaciones `@Entity`, la definición de campos base y la colección `List<Jugador> jugadores` con la anotación `@OneToMany`.
#### Importancia
Cumple el requisito de relacionar entidades de forma bidireccional mediante JPA.
#### Texto para el PDF
La entidad `Equipo` representa a los clubes dentro del sistema. Se ha establecido una relación bidireccional `@OneToMany` con la entidad Jugador, permitiendo acceder a la plantilla completa de forma eficiente desde la propia clase del equipo.

---

### REQUISITO: Entidad CartaFUT

#### Archivo
Ruta completa:
```text
/home/sergio/demofut/src/main/java/com/futmanager/demofut/entity/CartaFUT.java
```
#### Clase
`CartaFUT`
#### Captura obligatoria
Las anotaciones `@Entity`, y los atributos específicos como `tipoCarta`, `media`, `ritmo`, y la relación `@ManyToOne` con `Jugador`.
#### Importancia
Demuestra la complejidad añadida de gestionar cartas coleccionables sobre la base de jugadores reales, expandiendo la arquitectura inicial.
#### Texto para el PDF
Se ha implementado el modelo `CartaFUT` para representar las cartas coleccionables en el ecosistema Ultimate Team. Esta entidad hereda conceptualmente de Jugador mediante una relación relacional, pero incluye atributos específicos de rendimiento, diseño y mercado.

---

### REQUISITO: CRUD Completo

#### Archivo
Ruta completa:
```text
/home/sergio/demofut/src/main/java/com/futmanager/demofut/controller/JugadorController.java
```
#### Clase
`JugadorController`
#### Captura obligatoria
Los métodos que tengan las anotaciones `@GetMapping`, `@PostMapping`, `@PutMapping` y `@DeleteMapping`.
#### Importancia
Evidencia que la API RESTful implementa el ciclo completo de vida (Create, Read, Update, Delete) de una entidad mediante los verbos HTTP estándar.
#### Texto para el PDF
El controlador `JugadorController` expone los endpoints RESTful para la manipulación de datos, implementando los cuatro verbos HTTP estándar (GET, POST, PUT, DELETE) para garantizar un sistema CRUD completo, robusto y conectado con los servicios de persistencia.

---

## 2. FRONTEND

### REQUISITO: Proyecto React

#### Archivo
Ruta completa:
```text
/home/sergio/demofut/futmanager-front/package.json
```
#### Captura obligatoria
Las dependencias donde se vea `react`, `react-dom` y `react-router-dom`.
#### Importancia
Demuestra la configuración de un entorno Single Page Application (SPA) moderno para el lado del cliente.
#### Texto para el PDF
El frontal de la aplicación se ha desarrollado íntegramente con la librería React. El empaquetado y la inyección de dependencias constatan un entorno SPA moderno, utilizando enrutamiento dinámico en cliente y consumo de API asíncrono.

---

### CAPTURAS DE FRONTEND (Navegación en el navegador)

1. **Pantalla:** Listado de Jugadores / Cartas
   - **Acción:** Abrir el menú lateral y pulsar en "Jugadores" o "Cartas FUT".
   - **Captura:** La tabla o el grid de cartas visible, demostrando la operación GET (Read) del CRUD.
   - **Texto:** La interfaz principal permite visualizar los registros almacenados en base de datos mediante el consumo asíncrono del endpoint GET del backend.

2. **Pantalla:** Crear / Editar
   - **Acción:** Pulsar el botón de "Crear" (ej. "Crear Jugador" o "Nueva Carta") y rellenar algunos datos del formulario, sin llegar a guardar.
   - **Captura:** El modal o formulario abierto con los campos desplegados y listos para validar.
   - **Texto:** Los formularios en React gestionan el estado local y realizan las peticiones POST y PUT, implementando validaciones previas antes del envío a la API REST.

3. **Pantalla:** Eliminar
   - **Acción:** Tener un jugador o carta visible en lista y abrir el diálogo de confirmación de borrado al pulsar el icono de papelera.
   - **Captura:** El mensaje de advertencia "¿Estás seguro de eliminar...?" visible en pantalla.
   - **Texto:** Las operaciones críticas como el borrado (DELETE) requieren confirmación explícita del usuario a través de componentes modales para evitar pérdida accidental de datos.

---

## 3. TESTS

### REQUISITO: Tests Unitarios

#### Archivo
Ruta completa:
```text
/home/sergio/demofut/src/test/java/com/futmanager/demofut/JugadorServiceTest.java
```
#### Clase
`JugadorServiceTest`
#### Método
`testGuardarJugadorCorrectamente` y/o `testFiltrarPorMediaMinima`
#### Captura obligatoria
El método de test completo mostrando las anotaciones `@Test`, la preparación (`given/when`) y las aserciones (`assertEquals` / `assertNotNull`).
#### Importancia
Demuestra la implementación de pruebas unitarias usando JUnit y Mockito para asegurar la fiabilidad de la lógica de negocio en el Service.
#### Texto para el PDF
Se ha garantizado la estabilidad del código desarrollando una batería de pruebas unitarias sobre las capas de servicio (Services). Mediante JUnit y Mockito, se aísla la lógica de negocio e inyecta repositorios simulados para validar los casos de uso esperados y el manejo de excepciones.

---

### REQUISITO: Endpoint de Resultados de Tests

#### Archivo
Ruta completa:
```text
/home/sergio/demofut/src/main/java/com/futmanager/demofut/controller/TestController.java
```
#### Clase
`TestController`
#### Método
`getTestResults()`
#### Captura obligatoria
El método completo que devuelve el `Map<String, Object>` con la información de totalTests, passed y failed.
#### Importancia
Evidencia un controlador REST adicional dedicado a exponer métricas de calidad y salud de las pruebas hacia el cliente.
#### Texto para el PDF
Para visualizar de forma rápida la salud de la aplicación sin depender del terminal, se ha desarrollado un endpoint REST dedicado en `TestController` que formatea y expone la recopilación de los resultados y estado de las pruebas unitarias integradas.

---

### CAPTURAS DE TESTS (Consola y UI)

1. **Pantalla Front:** Panel de Tests
   - **Acción:** Entrar a la ruta de "Tests" en el menú de la aplicación React.
   - **Captura:** La barra de progreso en verde y la tabla mostrando los nombres de las clases y métodos de test "PASADOS".
   - **Texto:** El frontend interpreta y maqueta visualmente los datos del controlador de tests, proporcionando un dashboard de calidad de software inmediato al administrador.

2. **Consola:** Ejecución de Maven
   - **Acción:** Ejecutar en el terminal el siguiente comando y esperar a que acabe:
     ```bash
     ./mvnw test
     ```
   - **Captura:** El resumen final en color verde mostrando algo similar a: `[INFO] BUILD SUCCESS` y `Tests run: 42, Failures: 0, Errors: 0, Skipped: 0`.
   - **Texto:** La ejecución automatizada de pruebas mediante Maven Lifecycle valida la robustez de las compilaciones, pasando todos los escenarios definidos con éxito.

---

## 4. INTELIGENCIA ARTIFICIAL

### REQUISITO: Carpeta agents y Archivo Principal

#### Archivo
Ruta completa:
```text
/home/sergio/demofut/agents/Agents.md
```
#### Captura obligatoria
El principio del archivo donde se ven las tablas documentando cada agente utilizado, su archivo y su función.
#### Importancia
Cumple explícitamente el requisito del uso de IA como agente colaborador, manteniendo un registro transparente de la intervención del modelo.
#### Texto para el PDF
Siguiendo las pautas del proyecto, se ha empleado Inteligencia Artificial como un asistente de desarrollo. Las interacciones, roles delegados y documentación se encuentran centralizados en el directorio `agents`, demostrando un uso planificado, estructurado y documentado de las herramientas modernas.

---

### REQUISITO: Carpeta .codex

#### Archivo
Ruta completa:
```text
/home/sergio/demofut/.codex/AGENTS.md
```
#### Captura obligatoria
El árbol de ficheros en el IDE mostrando la existencia de la carpeta oculta `.codex` y su contenido de revisión arquitectónica.
#### Importancia
Comprueba el uso profundo de los agentes, los cuales han generado también archivos de contexto en un formato estándar oculto.
#### Texto para el PDF
El asistente de Inteligencia Artificial también ha mantenido el contexto del sistema a lo largo de sus iteraciones gracias a la creación de una arquitectura oculta de contexto en la carpeta `.codex`, demostrando comprensión del modelo de negocio en todo su ciclo vital.

---

## 5. DOCUMENTACIÓN

### REQUISITO: README Principal

#### Archivo
Ruta completa:
```text
/home/sergio/demofut/README.md
```
#### Captura obligatoria
La vista previa renderizada (Preview) en el IDE o en GitHub, mostrando el índice, características, tecnologías y pasos de despliegue.
#### Importancia
El manual del proyecto, básico para cualquier trabajo profesional para asegurar su despliegue y compresión.
#### Texto para el PDF
La documentación es la puerta de entrada al software. El `README.md` incluye las pautas detalladas de despliegue, el stack tecnológico y los detalles funcionales, asegurando que cualquier desarrollador externo pueda levantar y comprender el sistema sin ambigüedades.

---

## 6. EJERCICIO 4: DTOs Y MAPEO

### REQUISITO: Patrón DTO

#### Archivo
Ruta completa:
```text
/home/sergio/demofut/src/main/java/com/futmanager/demofut/dto/JugadorDTO.java
```
#### Clase
`JugadorDTO`
#### Captura obligatoria
Los atributos de la clase sin anotaciones `@Entity`, y las validaciones simples como `@NotNull` o el `equipoId`.
#### Importancia
Demuestra un conocimiento arquitectónico avanzado evitando exponer las entidades JPA y rompiendo dependencias cíclicas.
#### Texto para el PDF
Implementación del patrón Data Transfer Object (DTO). Se ha desacoplado la capa de persistencia de la capa de transporte; `JugadorDTO` representa los datos exactos requeridos por la API REST, eliminando el riesgo de recursión en las relaciones de base de datos e incrementando la seguridad.

---

### REQUISITO: Mapeo (Mapper)

#### Archivo
Ruta completa:
```text
/home/sergio/demofut/src/main/java/com/futmanager/demofut/mapper/JugadorMapper.java
```
#### Clase
`JugadorMapper`
#### Método
`toDTO` o `toEntity`
#### Captura obligatoria
La lógica de conversión de objeto Entity a DTO instanciando manualmente y asignando las propiedades (setters/getters).
#### Importancia
Garantiza el traslado de información segura y controlada entre capas.
#### Texto para el PDF
Se utilizan clases Mapper específicas (`JugadorMapper`) para realizar las transformaciones bidireccionales entre Entity y DTO. Esta capa intermedia asegura un mapeo preciso de propiedades y la correcta asignación de IDs en relaciones complejas antes de su serialización JSON.

---

### REQUISITO: Documentación Técnica de DTOs y Mapeo

#### Archivos
Rutas completas:
```text
/home/sergio/demofut/DTO.md
/home/sergio/demofut/MAPEO.md
```
#### Captura obligatoria
Vista previa (Preview) de `DTO.md` donde se explica "Qué es un DTO" y "Por qué se usan DTOs en este proyecto".
#### Importancia
Refuerza el valor académico demostrando la justificación teórica y no solo la copia de código.
#### Texto para el PDF
El ejercicio se complementa con una explicación teórica detallada sobre los motivos de aplicar este patrón arquitectónico, los flujos de comunicación entre capas y sus beneficios frente a la exposición directa de Entidades JPA, garantizando una compresión global de la arquitectura.

---

## CAPTURAS DE BASE DE DATOS

Para mostrar la base de datos debes:

1. **Pantalla:** Un cliente SQL (DBeaver, MySQL Workbench o la pestaña Database de IntelliJ).
2. **Acción:** Conectarte a `localhost:3309` (usuario/pass: root/root).
3. **Captura 1:** El árbol de tablas en el panel izquierdo mostrando `jugadores`, `equipos`, `cartas_fut`, etc.
4. **Captura 2:** Un `SELECT * FROM cartas_fut;` ejecutado, con la tabla de resultados visible en la zona inferior.
5. **Texto:** El modelo relacional ha sido correctamente generado por Hibernate, preservando claves primarias foráneas. Los datos persistidos mantienen la integridad estructural diseñada en la capa de entidades de Spring.

---

## TABLA RESUMEN Y PRIORIDADES

| Nº | Requisito | Archivo / Pantalla | Captura | Prioridad |
|---|---|---|---|---|
| 1 | Spring Boot | `DemofutApplication.java` | Anotación `@SpringBootApplication` y método main | ALTA |
| 2 | MySQL | `docker-compose.yml` | Bloque del servicio DB y puerto 3309 | MEDIA |
| 3 | Entidades | `Jugador.java` | `@Entity` y relación `@ManyToOne` | ALTA |
| 4 | CRUD API | `JugadorController.java` | Métodos POST, GET, PUT, DELETE | ALTA |
| 5 | React CRUD UI | Front - Crear/Editar Jugador | Formulario / Modal relleno | ALTA |
| 6 | React Listado UI | Front - Lista de Jugadores/Cartas | Tabla de registros consumidos del back | ALTA |
| 7 | Tests Service | `JugadorServiceTest.java` | Anotación `@Test` y aserciones | ALTA |
| 8 | Tests Endpoint | `TestController.java` | Método que retorna el Map de resumen de tests | MEDIA |
| 9 | Tests Consola | Terminal local | `./mvnw test` mostrando SUCCESS | ALTA |
| 10 | Panel Tests UI | Front - Página "Tests" | Tabla de Front end consumiendo test-results | MEDIA |
| 11 | Inteligencia Art. | `Agents.md` | Explicación inicial de los agentes y tabla | ALTA |
| 12 | Archivos IA | Árbol del IDE | Carpetas `agents/` y `.codex/` | MEDIA |
| 13 | Documentación | `README.md` | Vista previa del archivo de despliegue | MEDIA |
| 14 | Patrón DTO | `JugadorDTO.java` | Atributos simples y validaciones | ALTA |
| 15 | Patrón Mapper | `JugadorMapper.java` | Lógica de getters y setters | ALTA |
| 16 | Doc DTOs | `DTO.md` | Justificación teórica del proyecto | MEDIA |
| 17 | Evidencia DB | Cliente SQL o DBeaver | `SELECT * FROM cartas_fut;` con datos | ALTA |

---

## ORDEN RECOMENDADO DEL PDF

Para conseguir la **máxima nota**, el PDF debe seguir una narrativa de "Arquitectura a Presentación", demostrando primero las bases y luego cómo el usuario interactúa con ellas.

1. **Introducción y Arquitectura Base:** Capturas de `README.md`, `DemofutApplication.java` y `docker-compose.yml`.
2. **Modelo de Datos y Persistencia:** Capturas de las Entidades (`Jugador.java`, `Equipo.java`) y Cliente SQL mostrando las tablas.
3. **Ejercicio 4 - DTOs y Mapeo:** Capturas de `JugadorDTO.java`, `JugadorMapper.java` y `DTO.md`. *(Explicando que la información de Base de Datos se transforma antes de salir a la API)*.
4. **Capa API REST (CRUD):** Capturas del `JugadorController.java` con sus métodos HTTP.
5. **Capa Cliente (Frontend):** Capturas de React (`package.json`), listados (Read), formularios (Create/Update) y diálogos (Delete).
6. **Aseguramiento de Calidad (Tests):** Capturas de `JugadorServiceTest.java`, ejecución del terminal (`./mvnw test`) y Endpoint `TestController.java`.
7. **Innovación e Inteligencia Artificial:** Panel UI de tests en React, y finalizando con `Agents.md` y estructura de la IA en el proyecto.

---

## FUNCIONALIDAD RECOMENDADA PARA EL VÍDEO

### Nombre
**Simulador de Partidos Ultimate Team (Flujo Completo de la Carta al Campo)**

### Archivos implicados
- **Front:** `Simulador.jsx`, `api.js`
- **Controller:** `SimuladorController.java`
- **Service:** `UltimateTeamService.java` (lógica `simularPartidoConPlantillaValida`)
- **Entity:** `PartidoSimulado.java`
- **DTO/Mapper:** `PartidoSimuladoDTO.java`, `PartidoSimuladoMapper.java`

### Flujo completo
```text
Frontend (El usuario elige 2 plantillas y pulsa "Simular Partido")
↓
Controller (SimuladorController.java recibe el POST con las IDs de las plantillas)
↓
Service (UltimateTeamService evalúa la media, química, ataque y defensa para calcular el ganador y genera el evento)
↓
Entity/Mapper (Se crea PartidoSimulado.java, se guarda y se transforma a DTO)
↓
Repository (Persiste el histórico del partido en MySQL)
↓
Frontend (Recibe el DTO y muestra una animación o el marcador del partido y los goleadores)
```

### Capturas necesarias para la explicación (si se apoya con slides)
- El menú de selección de plantillas en el frontend.
- Un fragmento de código de `UltimateTeamService.java` donde se vea la fórmula matemática de comparar el (Ataque + Química) vs (Defensa + Química).
- El marcador final mostrado en pantalla con la UI de "Victoria" o "Derrota".

### Guion de explicación
*"Para demostrar la integración de todas las capas del proyecto, quiero enseñar el Simulador de Partidos. 
Desde el Frontend, el usuario pulsa un botón que envía una petición POST al `SimuladorController`. 
Este controlador no va directo a la base de datos, sino que delega en el `UltimateTeamService`. Aquí es donde ocurre la magia: recupero los jugadores usando el Patrón Repository, calculo las estadísticas matemáticas reales combinando Media y Química de los jugadores, determino un resultado de forma aleatoria sesgada y creo una entidad `PartidoSimulado`. 
Finalmente, para no enviar datos innecesarios de Hibernate que rompan el Front, uso `PartidoSimuladoMapper` para transformarlo en un DTO. El Frontend lee el JSON del DTO y pinta un marcador bonito en React."*
