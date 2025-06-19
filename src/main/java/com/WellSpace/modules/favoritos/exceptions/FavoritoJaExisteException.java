package com.WellSpace.modules.favoritos.exceptions;

public class FavoritoJaExisteException extends RuntimeException {
    public FavoritoJaExisteException(String message) {
        super(message);
    }
}
