package com.futmanager.demofut.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartidoSimulado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plantilla_id")
    private Plantilla plantilla;

    private String nombrePlantilla;
    private String rival;
    private Integer golesUsuario;
    private Integer golesRival;
    private String ganador;

    @Column(length = 1200)
    private String resumen;

    @Column(length = 800)
    private String mejoresJugadores;

    private String mvp;
    private Integer monedasGanadas;
    private Integer experienciaGanada;

    @Column(length = 2000)
    private String eventos;

    private LocalDateTime fecha;
}
