# Sobres

El sistema de sobres permite gastar monedas para recibir cartas aleatorias.

## Tipos

| Sobre | Coste |
|---|---:|
| bronce | 750 |
| plata | 2500 |
| oro | 7500 |
| oro premium | 15000 |
| especial | 25000 |

Cuanto mejor es el sobre, mas opciones hay de recibir cartas con media alta.

## Endpoint

```text
POST /api/sobres/abrir/{tipoSobre}
```

Ejemplo:

```text
POST /api/sobres/abrir/oro
```

Respuesta:

```text
tipoSobre
coste
monedasRestantes
cartas
```
