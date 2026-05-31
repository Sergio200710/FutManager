package com.futmanager.demofut.repository;

import com.futmanager.demofut.entity.Jugador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    List<Jugador> findByPosicion(String posicion);
    List<Jugador> findByMediaGreaterThanEqual(Integer media);
    List<Jugador> findByEquipoId(Long equipoId);
    List<Jugador> findByEquipoLigaDatosId(Long ligaId);

    @Query("""
            SELECT j FROM Jugador j
            WHERE (:liga IS NULL OR LOWER(j.liga) LIKE LOWER(CONCAT('%', :liga, '%')))
            AND (:equipo IS NULL OR LOWER(j.equipo.nombre) LIKE LOWER(CONCAT('%', :equipo, '%')))
            AND (:nacionalidad IS NULL OR LOWER(j.nacionalidad) LIKE LOWER(CONCAT('%', :nacionalidad, '%')))
            AND (:posicion IS NULL OR LOWER(j.posicion) = LOWER(:posicion))
            AND (:mediaMin IS NULL OR j.media >= :mediaMin)
            AND (:buscar IS NULL OR LOWER(j.nombre) LIKE LOWER(CONCAT('%', :buscar, '%'))
                OR LOWER(j.apellido) LIKE LOWER(CONCAT('%', :buscar, '%')))
            """)
    Page<Jugador> filtrar(String liga, String equipo, String nacionalidad, String posicion,
                          Integer mediaMin, String buscar, Pageable pageable);
}
