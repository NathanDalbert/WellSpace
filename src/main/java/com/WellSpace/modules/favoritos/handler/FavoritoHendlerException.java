package com.WellSpace.modules.favoritos.handler;
import com.WellSpace.modules.favoritos.exceptions.FavoritoJaExisteException;
import com.WellSpace.modules.favoritos.exceptions.FavoritoNaoEncontradoException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;


@RestControllerAdvice
public class FavoritoHendlerException {


    @ExceptionHandler(FavoritoJaExisteException.class)
    public ResponseEntity<Map<String, String>> handleFavoritoJaExisteException(FavoritoJaExisteException ex) {
        Map<String, String> errorResponse = Map.of("mensagem", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse); // HttpStatus.CONFLICT é o mesmo que o código 409
    }

    @ExceptionHandler(FavoritoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleFavoritoNaoEncontradoException(FavoritoNaoEncontradoException ex) {
        Map<String, String> errorResponse = Map.of("mensagem", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse); // HttpStatus.NOT_FOUND é o mesmo que o código 404
    }
}