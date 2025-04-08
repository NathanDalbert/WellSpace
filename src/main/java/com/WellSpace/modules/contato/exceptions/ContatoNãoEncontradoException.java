package com.WellSpace.modules.contato.exceptions;

import java.util.UUID;

public class ContatoNãoEncontradoException extends RuntimeException {
    public ContatoNãoEncontradoException(UUID contatoId) {
        super("Contato com ID " + contatoId + " não encontrado.");
    }
}

