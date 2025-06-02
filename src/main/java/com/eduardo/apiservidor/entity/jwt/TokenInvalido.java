package com.eduardo.apiservidor.entity.jwt;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "token_invalido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenInvalido {
    @Id
    @Column(name = "token", nullable = false, unique = true, length = 1024)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_expiracao", nullable = false)
    private Date dataExpiracao;

}
