package com.WellSpace.modules.usuario.services.interfaces;

import com.WellSpace.modules.usuario.DTO.UsuarioResponse;
import com.WellSpace.modules.usuario.DTO.UsuarioUpdateRequest;
import com.WellSpace.modules.usuario.domain.Usuario;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface UsuarioServiceInterface {



    UsuarioResponse buscarUsuarioPorId(UUID usuarioId);

    UsuarioResponse atualizarUsuario(UUID usuarioId, @Valid UsuarioUpdateRequest usuarioUpdateRequest);

    void deletarUsuario(UUID usuarioId);
    List<UsuarioResponse> buscarTodosUsuarios();


}
