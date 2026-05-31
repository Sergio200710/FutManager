package com.futmanager.demofut.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvolucionCarta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carta_id")
    private CartaFUT cartaFUT;

    private String nombreEvolucion;
    private String descripcion;
    private Boolean completada = false;
    private Integer mejoraMedia;
    private Integer mejoraRitmo;
    private Integer mejoraTiro;
    private Integer mejoraPase;
    private Integer mejoraRegate;
    private Integer mejoraDefensa;
    private Integer mejoraFisico;
}
