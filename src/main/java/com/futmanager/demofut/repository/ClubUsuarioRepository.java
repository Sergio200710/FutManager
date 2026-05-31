package com.futmanager.demofut.repository;

import com.futmanager.demofut.entity.ClubUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubUsuarioRepository extends JpaRepository<ClubUsuario, Long> {
}
