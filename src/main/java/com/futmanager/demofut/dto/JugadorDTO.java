package com.futmanager.demofut.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JugadorDTO {

    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    private String apellido;
    private String nombreCompleto;

    @NotBlank(message = "La posición no puede estar vacía")
    private String posicion;
    private String posicionPrincipal;
    private String posicionSecundaria;

    @NotBlank(message = "La nacionalidad no puede estar vacía")
    private String nacionalidad;

    @NotNull(message = "La edad no puede ser nula")
    @Min(value = 16, message = "La edad debe ser al menos 16")
    @Max(value = 45, message = "La edad debe ser como máximo 45")
    private Integer edad;

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

    private String liga;
    private String nombreLiga;
    private String foto;
    private String fotoUrl;
    private String altura;
    private String peso;

    @NotNull(message = "El equipo no puede ser nulo")
    private Long equipoId;

    private String equipoNombre;
    private String nombreEquipo;
}
