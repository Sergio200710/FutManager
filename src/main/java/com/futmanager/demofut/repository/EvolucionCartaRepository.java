package com.futmanager.demofut.repository;

import com.futmanager.demofut.entity.EvolucionCarta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvolucionCartaRepository extends JpaRepository<EvolucionCarta, Long> {
}
