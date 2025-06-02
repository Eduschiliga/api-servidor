package com.eduardo.apiservidor.exception.handler;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiError {
  private String erro;
  private String mensagem;

  public ApiError(HttpStatus status, String mensagem) {
    this.erro = status.getReasonPhrase();
    this.mensagem = mensagem;
  }
}
