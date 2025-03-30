package com.WellSpace.modules.usuario.services.mappers;

import com.WellSpace.modules.usuario.DTO.UsuarioRegristro;

import com.WellSpace.modules.usuario.DTO.UsuarioResponse;
import com.WellSpace.modules.usuario.domain.Usuario;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioMapper {

    // Corrigido: agora o parâmetro é UsuarioRequest e não String
    public Usuario toEntity(@Valid UsuarioRegristro usuarioRequest) {
        return Usuario.newUsuario(
                usuarioRequest.nome(),
                usuarioRequest.senha(),
                usuarioRequest.email(),
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
