package com.eduardo.apiservidor.model.dto.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailListaDTO {
    private String mensagem;
    private List<EmailDTO> emails;
}
