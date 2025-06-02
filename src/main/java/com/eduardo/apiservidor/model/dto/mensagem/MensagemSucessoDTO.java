package com.eduardo.apiservidor.model.dto.mensagem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MensagemSucessoDTO {
    private String mensagem;
}
