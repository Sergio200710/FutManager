package com.futmanager.demofut.repository;

import com.futmanager.demofut.entity.CartaFUT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartaFUTRepository extends JpaRepository<CartaFUT, Long> {

    @Query("""
            SELECT c FROM CartaFUT c
            WHERE (:liga IS NULL OR LOWER(c.liga) LIKE LOWER(CONCAT('%', :liga, '%')))
            AND (:club IS NULL OR LOWER(c.club) LIKE LOWER(CONCAT('%', :club, '%')))
            AND (:nacionalidad IS NULL OR LOWER(c.nacionalidad) LIKE LOWER(CONCAT('%', :nacionalidad, '%')))
            AND (:posicion IS NULL OR LOWER(c.posicion) = LOWER(:posicion))
            AND (:rareza IS NULL OR LOWER(c.rareza) LIKE LOWER(CONCAT('%', :rareza, '%')))
            AND (:mediaMin IS NULL OR c.media >= :mediaMin)
            AND (:buscar IS NULL OR LOWER(c.club) LIKE LOWER(CONCAT('%', :buscar, '%'))
                OR LOWER(c.nacionalidad) LIKE LOWER(CONCAT('%', :buscar, '%'))
                OR LOWER(c.nombreJugador) LIKE LOWER(CONCAT('%', :buscar, '%'))
                OR LOWER(c.jugador.nombre) LIKE LOWER(CONCAT('%', :buscar, '%')))
            """)
    Page<CartaFUT> filtrar(String liga, String club, String nacionalidad, String posicion,
                           String rareza, Integer mediaMin, String buscar, Pageable pageable);

    List<CartaFUT> findByEnClubTrue();
    List<CartaFUT> findByJugadorId(Long jugadorId);

    @Query("""
            SELECT c FROM CartaFUT c
            WHERE (:nombre IS NULL OR LOWER(COALESCE(c.nombreJugador, c.jugador.nombre, '')) LIKE LOWER(CONCAT('%', :nombre, '%')))
            AND (:club IS NULL OR LOWER(c.club) LIKE LOWER(CONCAT('%', :club, '%')))
            AND (:liga IS NULL OR LOWER(c.liga) LIKE LOWER(CONCAT('%', :liga, '%')))
            AND (:nacionalidad IS NULL OR LOWER(c.nacionalidad) LIKE LOWER(CONCAT('%', :nacionalidad, '%')))
            AND (:posicion IS NULL OR LOWER(c.posicion) = LOWER(:posicion))
            AND (:rareza IS NULL OR LOWER(c.rareza) LIKE LOWER(CONCAT('%', :rareza, '%')))
            AND (:mediaMin IS NULL OR c.media >= :mediaMin)
            AND (:mediaMax IS NULL OR c.media <= :mediaMax)
            AND (:precioMin IS NULL OR c.precioMonedas >= :precioMin)
            AND (:precioMax IS NULL OR c.precioMonedas <= :precioMax)
            """)
    Page<CartaFUT> buscar(String nombre, String club, String liga, String nacionalidad,
                          String posicion, String rareza, Integer mediaMin, Integer mediaMax,
                          Integer precioMin, Integer precioMax, Pageable pageable);

    @Query("""
            SELECT c FROM CartaFUT c
            WHERE (:nombreJugador IS NULL OR LOWER(COALESCE(c.nombreJugador, c.jugador.nombre, '')) LIKE LOWER(CONCAT('%', :nombreJugador, '%')))
            AND (:ligaId IS NULL OR c.jugador.equipo.ligaDatos.id = :ligaId)
            AND (:equipoId IS NULL OR c.jugador.equipo.id = :equipoId)
            AND (:nacionalidad IS NULL OR LOWER(COALESCE(c.jugador.nacionalidad, c.nacionalidad, '')) LIKE LOWER(CONCAT('%', :nacionalidad, '%')))
            AND (:posicion IS NULL OR LOWER(COALESCE(c.posicionCarta, c.posicion, '')) = LOWER(:posicion))
            AND (:rareza IS NULL OR LOWER(c.rareza) LIKE LOWER(CONCAT('%', :rareza, '%')))
            AND (:tipoCarta IS NULL OR LOWER(c.tipoCarta) LIKE LOWER(CONCAT('%', :tipoCarta, '%')))
            AND (:mediaMin IS NULL OR c.media >= :mediaMin)
            AND (:mediaMax IS NULL OR c.media <= :mediaMax)
            AND (:precioMin IS NULL OR c.precioMonedas >= :precioMin)
            AND (:precioMax IS NULL OR c.precioMonedas <= :precioMax)
            """)
    Page<CartaFUT> buscarNormalizado(String nombreJugador, Long ligaId, Long equipoId, String nacionalidad,
                                     String posicion, String rareza, String tipoCarta, Integer mediaMin,
                                     Integer mediaMax, Integer precioMin, Integer precioMax, Pageable pageable);
}
