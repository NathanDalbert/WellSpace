package com.WellSpace.modules.contato.exceptions;

public class ContatoJaExisteException extends RuntimeException {
    public ContatoJaExisteException(String message) {
        super(message);
    }
}
