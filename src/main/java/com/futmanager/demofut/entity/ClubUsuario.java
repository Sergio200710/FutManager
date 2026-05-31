package com.futmanager.demofut.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubUsuario {

    @Id
    private Long id;

    @NotBlank
    private String nombreClub;

    @PositiveOrZero
    private Integer monedas;

    @Min(1)
    private Integer nivel;

    @PositiveOrZero
    private Integer experiencia;

    @PositiveOrZero
    private Integer partidosJugados = 0;

    @PositiveOrZero
    private Integer victorias = 0;

    @PositiveOrZero
    private Integer empates = 0;

    @PositiveOrZero
    private Integer derrotas = 0;
}
