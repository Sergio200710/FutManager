package com.futmanager.demofut.repository;

import com.futmanager.demofut.entity.MonederoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonederoUsuarioRepository extends JpaRepository<MonederoUsuario, Long> {
}
