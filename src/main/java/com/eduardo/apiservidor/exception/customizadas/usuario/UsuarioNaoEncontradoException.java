package com.eduardo.apiservidor.exception.customizadas.usuario;

import lombok.Getter;

@Getter
public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }
}
