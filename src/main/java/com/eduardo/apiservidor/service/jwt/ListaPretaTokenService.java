package com.eduardo.apiservidor.service.jwt;

import com.eduardo.apiservidor.entity.jwt.TokenInvalido;
import com.eduardo.apiservidor.exception.customizadas.jwt.TokenJWTException;
import com.eduardo.apiservidor.model.dto.mensagem.MensagemSucessoDTO;
import com.eduardo.apiservidor.repository.jwt.TokenInvalidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class ListaPretaTokenService {
    private final TokenInvalidoRepository invalidatedTokenRepository;
    private final TokenService tokenService;

    @Transactional
    public MensagemSucessoDTO adicionarNaListaPreta(String tokenJwt) {
        if (tokenJwt == null || tokenJwt.isEmpty()) {
            throw new TokenJWTException("Token nullo ou vazio");
        }

        String token = tokenJwt.replace("Bearer ", "");

        try {
            Date dataExpiracao = tokenService.getDataExpiracao(token);
            if (dataExpiracao != null) {
                TokenInvalido invalidatedToken = new TokenInvalido(token, dataExpiracao);
                invalidatedTokenRepository.save(invalidatedToken);
                log.info("Token adicionado à lista de tokens invalidos: {}", token);
                return new MensagemSucessoDTO("Token adicionado à lista de tokens invalidos");
            }
            log.warn("Não foi possível obter a data de expiração para o token: {}", token);
            throw new TokenJWTException("Não foi possível obter a data de expiração para o token");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public boolean isTokenInvalidado(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        return invalidatedTokenRepository.existsById(token);
    }

    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void removerTokensExpiradosDaBlacklist() {
        Date agora = new Date();
        log.info("Executando limpeza de tokens expirados da blacklist antes de: {}", agora);
        int quantidadeAtual = (int) invalidatedTokenRepository.count();
        invalidatedTokenRepository.deleteByDataExpiracaoBefore(agora);
        int quantidadeDepoisDaLimpeza = (int) invalidatedTokenRepository.count();
        log.info("Limpeza da blacklist concluída. {} tokens removidos.", quantidadeAtual - quantidadeDepoisDaLimpeza);
    }
}
