package com.WellSpace.modules.salas.repository;

import com.WellSpace.modules.salas.domain.ENUM.DisponibilidadeSalaEnum;
import com.WellSpace.modules.salas.domain.Salas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SalasRepository extends JpaRepository<Salas, UUID> {
    List<Salas> findAll();
    List<Salas> findByDisponibilidadeSala(DisponibilidadeSalaEnum disponibilidadeSala);
}
