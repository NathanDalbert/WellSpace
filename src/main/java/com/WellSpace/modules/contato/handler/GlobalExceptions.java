package com.WellSpace.modules.contato.handler;

import com.WellSpace.modules.contato.exceptions.ContatoJaExisteException;
import com.WellSpace.modules.contato.exceptions.ContatoNãoEncontradoException;
import com.WellSpace.modules.contato.exceptions.DadosDeContatoInvalidosException;
import com.WellSpace.modules.contato.exceptions.ErroGenericoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptions {
    @ExceptionHandler(ContatoJaExisteException.class)
    public ResponseEntity<String> handleContatoJaExisteException(ContatoJaExisteException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ContatoNãoEncontradoException.class)
    public ResponseEntity<String> handleContatoNaoEncontradoException(ContatoNãoEncontradoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DadosDeContatoInvalidosException.class)
    public ResponseEntity<String> handleDadosDeContatoInvalidosException(DadosDeContatoInvalidosException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ErroGenericoException.class)
    public ResponseEntity<String> handleErroGenericoException(ErroGenericoException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
