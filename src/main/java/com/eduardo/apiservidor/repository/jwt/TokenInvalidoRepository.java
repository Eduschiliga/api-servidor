package com.eduardo.apiservidor.repository.jwt;

import com.eduardo.apiservidor.entity.jwt.TokenInvalido;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;

public interface TokenInvalidoRepository extends JpaRepository<TokenInvalido, String> {
    @Transactional
    void deleteByDataExpiracaoBefore(Date dataLimite);
}
