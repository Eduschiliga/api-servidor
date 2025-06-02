package com.eduardo.apiservidor.model.dto.usuario;

import com.eduardo.apiservidor.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetornoUsuarioDTO {
    private String mensagem;
    private UsuarioDTO usuario;
}
