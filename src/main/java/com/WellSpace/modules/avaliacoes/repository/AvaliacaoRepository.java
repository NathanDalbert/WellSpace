package com.WellSpace.modules.avaliacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.WellSpace.modules.avaliacoes.domain.Avaliacao;

import java.util.UUID;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, UUID> {

    @Query("SELECT AVG(a.quantEstrelas) FROM Avaliacao a WHERE a.sala.salasId = :salaId")
    Double mediaEstrelasPorSala(UUID salaId);
}
