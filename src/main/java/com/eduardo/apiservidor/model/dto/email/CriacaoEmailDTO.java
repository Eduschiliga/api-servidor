package com.eduardo.apiservidor.model.dto.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriacaoEmailDTO {
    private String mensagem;
    private EmailDTO email;
}
