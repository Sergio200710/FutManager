package com.futmanager.demofut.repository;

import com.futmanager.demofut.entity.MercadoCarta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MercadoCartaRepository extends JpaRepository<MercadoCarta, Long> {
}
