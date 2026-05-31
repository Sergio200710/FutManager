package com.futmanager.demofut.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaFUT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El tipo de carta no puede estar vacío")
    private String tipoCarta;

    private String nombreJugador;

    @NotBlank(message = "La rareza no puede estar vacía")
    private String rareza;

    @NotNull(message = "La media no puede ser nula")
    @Min(value = 1, message = "La media debe ser al menos 1")
    @Max(value = 99, message = "La media debe ser como máximo 99")
    private Integer media;

    @Min(value = 1, message = "El ritmo debe ser al menos 1")
    @Max(value = 99, message = "El ritmo debe ser como máximo 99")
    private Integer ritmo;

    @Min(value = 1, message = "El tiro debe ser al menos 1")
    @Max(value = 99, message = "El tiro debe ser como máximo 99")
    private Integer tiro;

    @Min(value = 1, message = "El pase debe ser al menos 1")
    @Max(value = 99, message = "El pase debe ser como máximo 99")
    private Integer pase;

    @Min(value = 1, message = "El regate debe ser al menos 1")
    @Max(value = 99, message = "El regate debe ser como máximo 99")
    private Integer regate;

    @Min(value = 1, message = "La defensa debe ser al menos 1")
    @Max(value = 99, message = "La defensa debe ser como máximo 99")
    private Integer defensa;

    @Min(value = 1, message = "El físico debe ser al menos 1")
    @Max(value = 99, message = "El físico debe ser como máximo 99")
    private Integer fisico;

    private String imagenJugador;
    private String imagenEscudo;
    private String imagenBandera;

    @NotBlank(message = "El color de carta no puede estar vacío")
    private String colorCarta;

    private String liga;
    private String club;
    private String nacionalidad;
    private String posicion;
    private String posicionCarta;

    @PositiveOrZero(message = "El precio no puede ser negativo")
    private Integer precioMonedas;

    private Boolean enClub = true;

    private Boolean transferible = true;

    @NotBlank(message = "El estilo de carta no puede estar vacío")
    private String estiloCarta;

    @OneToOne
    @JoinColumn(name = "jugador_id")
    @JsonBackReference
    private Jugador jugador;
}
