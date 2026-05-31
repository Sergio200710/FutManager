package com.futmanager.demofut.repository;

import com.futmanager.demofut.entity.PartidoSimulado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartidoSimuladoRepository extends JpaRepository<PartidoSimulado, Long> {
}
