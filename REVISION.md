# REVISION.md — Análisis y Mejora de Código

## Implementación incorrecta detectada

### Problema: Eliminación de un Jugador sin comprobar existencia previa

**Archivo:** `JugadorController.java`  
**Método:** `delete()`

### Código original (mejorable)

```java
@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (jugadorService.findById(id).isPresent()) {
        jugadorService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
}
```

### Por qué se puede mejorar

1. **Doble consulta innecesaria a la base de datos**: Se hace un `findById` y luego un `deleteById`, lo que genera dos consultas SQL separadas. Si hay mucha carga, esto es ineficiente.
2. **Código HTTP incorrecto**: Para una eliminación exitosa, el estándar REST recomienda devolver `204 No Content`, no `200 OK`.
3. **Lógica de negocio en el Controller**: La comprobación de existencia debería delegar en el Service, no hacerse directamente en el Controller.

### Corrección aplicada

```java
// En JugadorController.java:
@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (jugadorService.findById(id).isPresent()) {
        jugadorService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 en vez de 200
    }
    return ResponseEntity.notFound().build();
}
```

### Mejora adicional recomendada (nivel avanzado)

Mover la comprobación al Service y lanzar una excepción personalizada:

```java
// En JugadorService.java:
public void deleteById(Long id) {
    if (!jugadorRepository.existsById(id)) {
        throw new RuntimeException("Jugador no encontrado con id: " + id);
    }
    jugadorRepository.deleteById(id);
}
```

Así el Controller queda limpio y la lógica de negocio está centralizada en el Service.
