package com.WellSpace.modules.salas.handler;

import com.WellSpace.modules.salas.exceptions.SalaHJaExisteException;
import com.WellSpace.modules.salas.exceptions.SalaNaoEncontradaException;
import com.WellSpace.modules.salas.exceptions.TempoInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHendlerException  {


    @ExceptionHandler(TempoInvalidoException.class)
    public ResponseEntity<String> handleTempoInvalidoException(TempoInvalidoException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }


    @ExceptionHandler(SalaHJaExisteException.class)
    public ResponseEntity<String> handleSalaHJaExisteException(SalaHJaExisteException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }
    @ExceptionHandler(SalaNaoEncontradaException.class)
    public ResponseEntity<String> handleSalaNaoEncontradaException(SalaNaoEncontradaException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>("Erro interno no servidor: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
