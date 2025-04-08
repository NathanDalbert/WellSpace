package com.WellSpace.modules.contato.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.WellSpace.modules.contato.domain.Contato;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository

public interface ContatoRepository extends JpaRepository<Contato, UUID> {

    Optional<Contato> findById(UUID contatoId);

    List<Contato> findByUsuario_UsuarioId(UUID usuarioId);

    boolean existsByUsuario_UsuarioIdAndContato(UUID usuarioId, String contato);

}

