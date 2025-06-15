package com.eduardo.apiservidor.exception.customizadas.email;

public class RemetenteInvalidoException extends RuntimeException {
    public RemetenteInvalidoException(String message) {
        super(message);
    }
}
