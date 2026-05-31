package com.futmanager.demofut.controller;

import com.futmanager.demofut.dto.MonederoDTO;
import com.futmanager.demofut.service.MonederoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/monedero")
@CrossOrigin(origins = "*")
public class MonederoController {

    @Autowired
    private MonederoService monederoService;

    @GetMapping
    public MonederoDTO getMonedero() {
        return monederoService.getMonedero();
    }
}
