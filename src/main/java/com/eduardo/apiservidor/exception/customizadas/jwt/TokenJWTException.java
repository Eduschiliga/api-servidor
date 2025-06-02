package com.eduardo.apiservidor.exception.customizadas.jwt;

import lombok.Getter;

@Getter
public class TokenJWTException extends RuntimeException {

  public TokenJWTException(String message) {
    super(message);
  }
}
