package com.futmanager.demofut.repository;

import com.futmanager.demofut.entity.Sobre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SobreRepository extends JpaRepository<Sobre, Long> {
}
