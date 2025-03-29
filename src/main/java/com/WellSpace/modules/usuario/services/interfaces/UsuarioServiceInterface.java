package com.WellSpace.modules.usuario.services.interfaces;

import com.WellSpace.modules.usuario.DTO.UsuarioRequest;
import com.WellSpace.modules.usuario.DTO.UsuarioResponse;
import com.WellSpace.modules.usuario.DTO.UsuarioUpdateRequest;
import jakarta.validation.Valid;

import java.util.UUID;

public interface UsuarioServiceInterface {

    UsuarioResponse criarUsuario(@Valid UsuarioRequest usuarioRequest);

    UsuarioResponse buscarUsuarioPorId(UUID usuarioId);

    UsuarioResponse atualizarUsuario(UUID usuarioId, @Valid UsuarioUpdateRequest usuarioUpdateRequest);

    void deletarUsuario(UUID usuarioId);


}
