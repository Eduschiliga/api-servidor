package com.eduardo.apiservidor.model.dto.usuario;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CriacaoUsuarioDTO {
  private String nome;
  private String senha;
  private String email;
}
