package com.WellSpace.modules.usuario.exceptions;

public class SenhaIncorretaException extends RuntimeException {
    public SenhaIncorretaException(String message) {
        super(message);
    }
}
