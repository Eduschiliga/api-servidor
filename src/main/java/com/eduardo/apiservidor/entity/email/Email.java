package com.eduardo.apiservidor.entity.email;

import com.eduardo.apiservidor.model.enums.StatusEmail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token_invalido")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id", nullable = false)
    private Long emailId;

    @Column(name = "assunto", nullable = false)
    private String assunto;

    @Column(name = "email_destinatario", nullable = false)
    private String emailDestinatario;

    @Column(name = "email_remetente", nullable = false)
    private String emailRemetente;

    @Column(name = "corpo", nullable = false)
    private String corpo;

    @Column(name = "data_envio", nullable = false)
    private LocalDate dataEnvio;

    @Column(name = "status")
    private StatusEmail status;

    @PrePersist
    public void prePersist(){
        setEmailId(null);
        setDataEnvio(LocalDate.now());
    }
}


