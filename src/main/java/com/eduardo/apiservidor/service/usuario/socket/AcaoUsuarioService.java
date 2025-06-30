package com.eduardo.apiservidor.service.usuario.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AcaoUsuarioService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ConcurrentHashMap<String, Long> activeUsers = new ConcurrentHashMap<>();

    private static final long INACTIVE_TIMEOUT_MS = 86400000;

    public void usuarioLogado(String email) {
        activeUsers.put(email, Instant.now().toEpochMilli());
        broadcastUserUpdate();
    }

    public void usuarioDesconectado(String email) {
        activeUsers.remove(email);
        broadcastUserUpdate();
    }

    public void salvarAtividade(String email) {
        activeUsers.put(email, Instant.now().toEpochMilli());
    }

    public Set<String> buscarUsuariosAtivos() {
        return activeUsers.keySet();
    }

    public void limparUsuariosInativos() {
        long now = Instant.now().toEpochMilli();
        boolean changed = activeUsers.entrySet()
                .removeIf(entry -> (now - entry.getValue()) > INACTIVE_TIMEOUT_MS);
        if (changed) {
            broadcastUserUpdate();
        }
    }

    private void broadcastUserUpdate() {
        messagingTemplate.convertAndSend("/topic/usuarios/ativos", buscarUsuariosAtivos());
    }
}