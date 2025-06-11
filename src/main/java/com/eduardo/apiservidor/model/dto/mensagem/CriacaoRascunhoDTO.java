package com.eduardo.apiservidor.model.dto.mensagem;

import com.eduardo.apiservidor.model.dto.email.rascunho.RascunhoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriacaoRascunhoDTO {
    private String mensagem;
    private RascunhoDTO rascunho;
}
