# AGENTES

IMPORTANTE: Antes de tocar cualquier línea de código, los agentes (como yo) deben leer todos los archivos contenidos en esta carpeta `.codex` para entender el contexto, la arquitectura y las reglas de este proyecto.

---

## Documentación de Agentes Usados (Auditoría del Proyecto)

Esta sección justifica y detalla de forma transparente la colaboración de Inteligencia Artificial como asistente durante el desarrollo de FutManager.

| Agente | Archivo de Contexto | Función en el Proyecto |
|---|---|---|
| **Agente de Código (Full Stack)** | `.codex/backend.md` / `.codex/frontend.md` | Ayuda en la redacción de código repetitivo (Entidades, Repositorios, Servicios) manteniendo una arquitectura MVC sencilla propia de 1º DAM. |
| **Agente de Datos (MySQL)** | `DemoDataLoader.java` | Refactorización de la base de datos para utilizar exclusivamente jugadores, equipos y ligas **reales**, e inyección en MySQL. |
| **Agente de Testing (JUnit 5)** | `JugadorServiceTest.java` | Revisión, corrección y redacción de batería de tests unitarios (con Mockito) para garantizar el funcionamiento del backend sin necesidad de levantar servicios. |
| **Agente de Documentación** | `AGENTS.md` / `README.md` | Estructuración de los pasos para la entrega, explicación de la arquitectura y redacción de documentación técnica general. |
