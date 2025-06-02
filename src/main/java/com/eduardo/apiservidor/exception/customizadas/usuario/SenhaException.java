package com.eduardo.apiservidor.exception.customizadas.usuario;

import lombok.Getter;

@Getter
public class SenhaException extends RuntimeException {
  public SenhaException(String message) {
    super(message);
  }
}
