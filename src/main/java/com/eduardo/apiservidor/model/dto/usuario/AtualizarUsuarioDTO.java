package com.eduardo.apiservidor.model.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarUsuarioDTO {
    private String nome;
    private String senha;
}
