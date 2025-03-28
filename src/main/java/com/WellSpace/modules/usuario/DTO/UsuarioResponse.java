package com.WellSpace.modules.usuario.DTO;

import java.util.UUID;

public record UsuarioResponse(UUID usuarioId, String nome, String email, String senha,String fotoPerfil, Boolean integridade, String dataNascimento) {

}
