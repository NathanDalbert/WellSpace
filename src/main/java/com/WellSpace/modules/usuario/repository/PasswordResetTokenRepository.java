package com.WellSpace.modules.usuario.repository;


import com.WellSpace.modules.usuario.domain.PasswordResetToken;
import com.WellSpace.modules.usuario.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByUsuario(Usuario usuario);

    @Transactional
    void deleteByUsuario(Usuario usuario);
}