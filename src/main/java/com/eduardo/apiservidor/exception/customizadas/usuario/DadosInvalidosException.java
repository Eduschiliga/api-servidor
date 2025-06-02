package com.eduardo.apiservidor.exception.customizadas.usuario;

import lombok.Getter;

@Getter
public class DadosInvalidosException extends RuntimeException {
  public DadosInvalidosException(String message) {
    super(message);
  }
}
