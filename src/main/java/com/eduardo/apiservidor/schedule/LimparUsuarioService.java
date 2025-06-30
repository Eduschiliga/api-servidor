package com.eduardo.apiservidor.schedule;

import com.eduardo.apiservidor.service.usuario.socket.AcaoUsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LimparUsuarioService {

    private final AcaoUsuarioService userActivityService;

    @Scheduled(fixedRate = 300000)
    public void reportCurrentTime() {
        log.info("Executando limpeza de usuários inativos.");
        userActivityService.limparUsuariosInativos();
    }
}