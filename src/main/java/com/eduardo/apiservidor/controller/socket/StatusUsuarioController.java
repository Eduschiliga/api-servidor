package com.eduardo.apiservidor.controller.socket;

import com.eduardo.apiservidor.service.usuario.socket.AcaoUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class StatusUsuarioController {
    private final AcaoUsuarioService userActivityService;

    @MessageMapping("/usuarios/ativos")
    @SendTo("/topic/usuarios/ativos")
    public Set<String> getActiveUsers() {
        return userActivityService.buscarUsuariosAtivos();
    }
}
