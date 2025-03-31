package com.WellSpace.modules.usuario.exceptions;

public class UsuarioJaExistenteException extends RuntimeException{
    public UsuarioJaExistenteException(String message) {
        super(message);
    }
}
