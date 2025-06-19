package com.WellSpace.modules.usuario.services.mappers;

import com.WellSpace.modules.usuario.DTO.UsuarioRegristro;

import com.WellSpace.modules.usuario.DTO.UsuarioResponse;
import com.WellSpace.modules.usuario.domain.Usuario;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioMapper {


    public Usuario toEntity(@Valid UsuarioRegristro usuarioRequest, String fotoUrl) {
        return Usuario.newUsuario(
                usuarioRequest.nome(),
                usuarioRequest.email(),
                usuarioRequest.senha(),
                fotoUrl,
                usuarioRequest.integridade(),
                usuarioRequest.dataNascimento()
        );
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getUsuarioId(),
                usuario.getNome(),
                usuario.getEmail(),
                Optional.ofNullable(usuario.getFotoPerfil()),
                Optional.ofNullable(usuario.getIntegridade()),
                Optional.ofNullable(usuario.getDataNascimento()),
                usuario.getUsuarioRole() != null ? usuario.getUsuarioRole() : null
        );
    }
}
