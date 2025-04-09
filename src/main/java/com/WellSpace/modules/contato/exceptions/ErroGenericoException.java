package com.WellSpace.modules.contato.exceptions;

public class ErroGenericoException extends RuntimeException {
    public ErroGenericoException(String message) {
        super("Erro no serviço: " + message);
    }
}
