package com.WellSpace.modules.localidades.repository;

import com.WellSpace.modules.localidades.domain.Localidades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LocalidadesRepository extends JpaRepository<Localidades, UUID> {
    List<Localidades> findByNomeLocal(String nomeLocal);
}
