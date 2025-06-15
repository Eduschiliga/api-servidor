package com.eduardo.apiservidor.exception.customizadas.email;

public class EmailNaoEncontradoException extends RuntimeException {
    public EmailNaoEncontradoException(String message) {
        super(message);
    }
}
