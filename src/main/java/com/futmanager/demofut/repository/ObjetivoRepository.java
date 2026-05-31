package com.futmanager.demofut.repository;

import com.futmanager.demofut.entity.Objetivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjetivoRepository extends JpaRepository<Objetivo, Long> {
}
