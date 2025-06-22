package com.eduardo.apiservidor.model.dto.email;

import com.eduardo.apiservidor.model.enums.StatusEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {
    private Long emailId;
    private String assunto;
    private String emailDestinatario;
    private String emailRemetente;
    private String corpo;
    private StatusEmail status;
    private LocalDate dataEnvio;
}
