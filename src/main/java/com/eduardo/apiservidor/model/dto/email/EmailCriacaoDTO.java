package com.eduardo.apiservidor.model.dto.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class EmailCriacaoDTO {
    private String assunto;
    private String emailDestinatario;
    private String corpo;
}

