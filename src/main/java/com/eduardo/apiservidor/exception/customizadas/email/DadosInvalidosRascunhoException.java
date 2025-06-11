package com.eduardo.apiservidor.exception.customizadas.email;

import lombok.Getter;

@Getter
public class DadosInvalidosRascunhoException extends RuntimeException {
    public DadosInvalidosRascunhoException(String message) {
        super(message);
    }
}
