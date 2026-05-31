package com.futmanager.demofut.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    
    private String liga;
    private String pais;
    private String ciudad;
    private String escudo;
    private String escudoUrl;
    private String estadio;

    @ManyToOne
    @JoinColumn(name = "liga_id")
    @JsonBackReference
    private Liga ligaDatos;

    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Jugador> jugadores;

    public Equipo(Long id, String nombre, String liga, String pais, List<Jugador> jugadores) {
        this.id = id;
        this.nombre = nombre;
        this.liga = liga;
        this.pais = pais;
        this.jugadores = jugadores;
    }
}
