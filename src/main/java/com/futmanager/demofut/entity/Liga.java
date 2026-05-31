package com.futmanager.demofut.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Liga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;

    private String pais;
    private String temporada;
    private String logo;
    private String logoUrl;
    private Integer nivel;

    @OneToMany(mappedBy = "ligaDatos")
    @JsonManagedReference
    private List<Equipo> equipos;
}
