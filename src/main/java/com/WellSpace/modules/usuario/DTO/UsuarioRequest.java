package com.WellSpace.modules.usuario.DTO;

public record UsuarioRequest( String nome, String email,String senha, String fotoPerfil, Boolean integridade, String dataNascimento) {

}
