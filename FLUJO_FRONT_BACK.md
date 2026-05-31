# FLUJO FRONT → BACK

## Ejemplo: Crear un Jugador

### 1. Frontend (React)

El usuario rellena el formulario en `Jugadores.jsx` y pulsa "Crear".

```jsx
// Jugadores.jsx
const handleSubmit = async (e) => {
  e.preventDefault();
  const payload = { nombre: "Pedri", posicion: "MC", media: 88, ... };
  await createJugador(payload); // llama a api.js
};
```

```js
// api.js
export const createJugador = (jugador) => api.post('/api/jugadores', jugador);
// → HTTP POST http://localhost:8080/api/jugadores
```

---

### 2. Controller (recibe la petición)

```java
// JugadorController.java
@PostMapping
public ResponseEntity<?> create(@Valid @RequestBody Jugador jugador) {
    // Comprueba que el equipo existe
    var equipoOpt = equipoService.findById(jugador.getEquipo().getId());
    jugador.setEquipo(equipoOpt.get());
    return ResponseEntity.ok(jugadorService.save(jugador));
}
```

---

### 3. Service (lógica de negocio)

```java
// JugadorService.java
public Jugador save(Jugador jugador) {
    return jugadorRepository.save(jugador);
}
```

---

### 4. Repository (consulta la base de datos)

```java
// JugadorRepository.java
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    // Spring Data JPA genera el SQL automáticamente:
    // INSERT INTO jugador (nombre, posicion, media, ...) VALUES (...)
}
```

---

### 5. Respuesta al frontend

Spring Boot serializa el objeto `Jugador` guardado a JSON y lo devuelve:

```json
{
  "id": 6,
  "nombre": "Pedri",
  "posicion": "MC",
  "media": 88,
  "ritmo": 78,
  "tiro": 75,
  "pase": 88,
  "defensa": 68,
  "fisico": 70
}
```

React actualiza el estado y muestra la nueva carta en pantalla.
