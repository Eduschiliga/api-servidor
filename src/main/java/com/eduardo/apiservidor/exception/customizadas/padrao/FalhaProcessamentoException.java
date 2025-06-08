package com.eduardo.apiservidor.exception.customizadas.padrao;

public class FalhaProcessamentoException extends RuntimeException {
    public FalhaProcessamentoException(String message) {
        super(message);
    }
}
