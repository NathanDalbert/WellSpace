package com.WellSpace.modules.favoritos.repository;

import com.WellSpace.modules.favoritos.domain.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavoritoRepository extends JpaRepository<Favorito, UUID> {

    Optional<Favorito> findByUsuarioUsuarioIdAndSalaSalasId(UUID usuarioId, UUID salaId);

    List<Favorito> findByUsuarioUsuarioId(UUID usuarioId);
}
