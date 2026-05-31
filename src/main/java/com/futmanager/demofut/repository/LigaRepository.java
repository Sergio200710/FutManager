package com.futmanager.demofut.repository;

import com.futmanager.demofut.entity.Liga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LigaRepository extends JpaRepository<Liga, Long> {
    Optional<Liga> findByNombreIgnoreCase(String nombre);
}
