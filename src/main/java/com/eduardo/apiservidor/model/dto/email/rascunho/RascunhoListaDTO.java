package com.eduardo.apiservidor.model.dto.email.rascunho;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RascunhoListaDTO {
    private String mensagem;
    private List<RascunhoDTO> rascunhos;
}
