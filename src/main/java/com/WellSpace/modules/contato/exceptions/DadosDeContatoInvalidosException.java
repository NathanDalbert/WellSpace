package com.WellSpace.modules.contato.exceptions;

public class DadosDeContatoInvalidosException extends RuntimeException {
    public DadosDeContatoInvalidosException(String message) {
        super("Dados inválidos para o contato: " + message);
    }
}
