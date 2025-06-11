package com.eduardo.apiservidor.model.dto.email.rascunho;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RascunhoDTO {
    private Long rascunhoId;
    private String assunto;
    private String emailDestinatario;
    private String corpo;
}
