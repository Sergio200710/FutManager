# Animacion de sobres

La pantalla de sobres tiene una animacion propia inspirada en juegos de futbol,
sin copiar un diseno oficial.

## Flujo visual

1. El usuario pulsa abrir sobre.
2. Aparece una pantalla oscura.
3. Se muestra un sobre cerrado.
4. El sobre brilla.
5. Aparecen luces laterales.
6. Se revelan pasos: rareza, nacionalidad, posicion, club y jugador.
7. Se muestra la carta principal.
8. Si la carta es especial o tiene media alta, aparece brillo y confetti.
9. Se muestra el grid final con las cartas obtenidas.

## Clases CSS principales

- `pack-opening-screen`
- `pack-glow`
- `pack-light-left`
- `pack-light-right`
- `pack-reveal-step`
- `card-final-reveal`
- `special-confetti`

## Guardado de cartas

Cuando se abre un sobre, las cartas obtenidas quedan marcadas como cartas del club.
Se pueden consultar con:

```text
GET /api/cartas/mis-cartas
```

Tambien se pueden usar en plantillas o vender desde mercado.
