package com.futmanager.demofut.controller;

import com.futmanager.demofut.service.ImportadorDatosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/importar")
@CrossOrigin(origins = "*")
public class ImportadorDatosController {

    @Autowired
    private ImportadorDatosService importadorDatosService;

    @GetMapping("/ligas")
    public Map<String, Object> importarLigas() {
        return importadorDatosService.importarLigas();
    }

    @GetMapping("/equipos")
    public Map<String, Object> importarEquipos() {
        return importadorDatosService.importarEquipos();
    }

    @GetMapping("/jugadores")
    public Map<String, Object> importarJugadores() {
        return importadorDatosService.importarJugadores();
    }

    @GetMapping("/cartas")
    public Map<String, Object> importarCartas() {
        return importadorDatosService.importarCartas();
    }

    @GetMapping("/todo")
    public Map<String, Object> importarTodo() {
        return importadorDatosService.importarTodo();
    }

    @GetMapping("/equipos-reales")
    public Map<String, Object> importarEquiposReales() {
        return importadorDatosService.importarEquiposReales();
    }

    @GetMapping("/jugadores-reales")
    public Map<String, Object> importarJugadoresReales() {
        return importadorDatosService.importarJugadoresReales();
    }

    @GetMapping("/cartas-reales")
    public Map<String, Object> importarCartasReales() {
        return importadorDatosService.importarCartasReales();
    }

    @GetMapping("/todo-real")
    public Map<String, Object> importarTodoReal() {
        return importadorDatosService.importarTodoReal();
    }
}
