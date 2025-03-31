package com.WellSpace.modules.usuario.handler;

import com.WellSpace.modules.usuario.exceptions.SenhaIncorretaException;
import com.WellSpace.modules.usuario.exceptions.UsuarioJaCadastradoException;
import com.WellSpace.modules.usuario.exceptions.UsuarioJaExistenteException;
import com.WellSpace.modules.usuario.exceptions.UsuarioNaoAutorizadoException;
import com.WellSpace.modules.usuario.exceptions.UsuarioNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UsuarioExceptionHandler {


    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException ex) {
        ErrorResponse errorResponse = new ErrorResponse("USUARIO_NAO_ENCONTRADO", "Usuário não encontrado.");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsuarioJaExistenteException.class)
    public ResponseEntity<ErrorResponse> handleUsuarioJaExistente(UsuarioJaExistenteException ex) {
        ErrorResponse errorResponse = new ErrorResponse("USUARIO_JA_EXISTENTE", "O e-mail informado já está cadastrado.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UsuarioNaoAutorizadoException.class)
    public ResponseEntity<ErrorResponse> handleUsuarioNaoAutorizado(UsuarioNaoAutorizadoException ex) {
        ErrorResponse errorResponse = new ErrorResponse("USUARIO_NAO_AUTORIZADO", "Você não tem permissão para acessar esse recurso.");
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(SenhaIncorretaException.class)
    public ResponseEntity<ErrorResponse> handleSenhaIncorreta(SenhaIncorretaException ex) {
        ErrorResponse errorResponse = new ErrorResponse("SENHA_INCORRETA", "A senha informada está incorreta.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UsuarioJaCadastradoException.class)
    public ResponseEntity<ErrorResponse> handleUsuarioJaCadastrado(UsuarioJaCadastradoException ex) {
        ErrorResponse errorResponse = new ErrorResponse("USUARIO_JA_CADASTRADO", "Este usuário já está registrado no sistema.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("ERRO_INTERNO_SERVIDOR", "Erro interno no servidor: " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
