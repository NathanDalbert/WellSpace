package com.WellSpace.modules.usuario.exceptions;

public class UsuarioNaoAutorizadoException extends  RuntimeException {
    public UsuarioNaoAutorizadoException(String message) {
        super(message);
    }
}
