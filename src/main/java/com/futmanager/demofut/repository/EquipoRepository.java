package com.futmanager.demofut.repository;

import com.futmanager.demofut.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    Optional<Equipo> findByNombreIgnoreCase(String nombre);
    List<Equipo> findByLigaDatosId(Long ligaId);
}
