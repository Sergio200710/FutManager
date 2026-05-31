# MEJORAS DEL BACKEND - FutManager

Este documento identifica malas prácticas del backend actual y explica cómo mejorarlas.
Es un ejercicio de análisis crítico del código entregado.

---

## Mala práctica 1: Lógica de negocio en el Controller

### ¿Qué está mal?
En `JugadorController.java`, el método `create` y `update` buscan y asignan el equipo directamente dentro del controlador:

```java
// JugadorController.java
@PostMapping
public Jugador create(@RequestBody Jugador jugador) {
    if (jugador.getEquipo() != null && jugador.getEquipo().getId() != null) {
        equipoService.findById(jugador.getEquipo().getId()).ifPresent(jugador::setEquipo);
    }
    return jugadorService.save(jugador);
}
```

El controlador solo debería recibir la petición y delegarla al servicio. **No debe contener lógica de negocio.**

### ¿Cómo mejorarlo?
Mover esa lógica al servicio:

```java
// JugadorService.java (mejorado)
public Jugador crearJugador(Jugador jugador) {
    if (jugador.getEquipo() != null && jugador.getEquipo().getId() != null) {
        Equipo equipo = equipoRepository.findById(jugador.getEquipo().getId())
            .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        jugador.setEquipo(equipo);
    }
    return jugadorRepository.save(jugador);
}
```

---

## Mala práctica 2: Falta de validaciones en los datos de entrada

### ¿Qué está mal?
Las entidades no tienen ninguna validación. Se pueden crear jugadores con edad negativa, media mayor de 99, o nombre vacío:

```java
// Equipo.java - Sin validaciones
private String nombre;      // Puede ser null o vacío
private Double presupuesto; // Puede ser negativo
```

### ¿Cómo mejorarlo?
Usar las anotaciones de `jakarta.validation` que ya están en el proyecto:

```java
// Jugador.java (mejorado)
@NotBlank(message = "El nombre no puede estar vacío")
private String nombre;

@Min(value = 1, message = "La edad debe ser mayor que 0")
@Max(value = 45, message = "La edad no puede superar 45 años")
private Integer edad;

@Min(value = 1, message = "La media debe estar entre 1 y 99")
@Max(value = 99, message = "La media no puede superar 99")
private Integer media;
```

Y en el controller añadir `@Valid`:
```java
@PostMapping
public Jugador create(@Valid @RequestBody Jugador jugador) { ... }
```

---

## Mala práctica 3: Optional mal usado en los Controllers

### ¿Qué está mal?
En los métodos `update` y `delete` se llama a `findById` dos veces: una para comprobar si existe y otra para obtener el objeto. Esto hace dos consultas a la base de datos innecesariamente:

```java
// EquipoController.java - Dos llamadas a la BD
@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (equipoService.findById(id).isPresent()) {  // 1ª consulta
        equipoService.deleteById(id);               // 2ª consulta (deleteById también busca)
        return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
}
```

### ¿Cómo mejorarlo?
Usar el `Optional` correctamente en una sola consulta:

```java
// EquipoController.java (mejorado)
@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) {
    return equipoService.findById(id)
        .map(equipo -> {
            equipoService.deleteById(id);
            return ResponseEntity.ok().<Void>build();
        })
        .orElse(ResponseEntity.notFound().build());
}
```

---

## Mala práctica 4: Falta de manejo de errores global

### ¿Qué está mal?
Si ocurre una excepción (por ejemplo, un ID que no existe), Spring devuelve un error genérico con el stack trace completo. Esto es un problema de seguridad y da mala experiencia al usuario.

### ¿Cómo mejorarlo?
Crear un `@ControllerAdvice` con un manejador de excepciones global:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
```
