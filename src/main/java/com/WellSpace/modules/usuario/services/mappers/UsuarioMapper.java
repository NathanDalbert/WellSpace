package com.WellSpace.modules.usuario.services.mappers;

import com.WellSpace.modules.usuario.DTO.UsuarioRequest;
import com.WellSpace.modules.usuario.DTO.UsuarioResponse;
import com.WellSpace.modules.usuario.domain.Usuario;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequest usuarioRequest) {
        return Usuario.newUsuario(
                usuarioRequest.nome(),
                usuarioRequest.email(),
                usuarioRequest.senha(),
                usuarioRequest.fotoPerfil(),
                usuarioRequest.integridade(),
                usuarioRequest.dataNascimento()
        );


    }

    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getUsuario_id(),
                usuario.getNome(),
                usuario.getEmail(),
                Optional.ofNullable(usuario.getFotoPerfil()),
                Optional.ofNullable(usuario.getIntegridade()),
                Optional.ofNullable(usuario.getDataNascimento())
        );
    }
}
