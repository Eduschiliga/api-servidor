package com.eduardo.apiservidor.service.validacao;

import com.eduardo.apiservidor.entity.usuario.Usuario;
import com.eduardo.apiservidor.exception.customizadas.email.DadosInvalidosRascunhoException;
import com.eduardo.apiservidor.exception.customizadas.usuario.UsuarioNaoEncontradoException;
import com.eduardo.apiservidor.model.dto.email.EmailCriacaoDTO;
import com.eduardo.apiservidor.model.dto.usuario.CriacaoUsuarioDTO;
import com.eduardo.apiservidor.service.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailValidacaoService {
    private UsuarioService usuarioService;

    public void validarEmail(EmailCriacaoDTO emailCriacaoDTO) {
        log.info("Validando dados do Email: {}", emailCriacaoDTO);

        validarDestinatario(emailCriacaoDTO.getEmailDestinatario());
        validarCorpo(emailCriacaoDTO.getCorpo());
        validarAssunto(emailCriacaoDTO.getAssunto());

        log.info("Email validado");
    }

    private void validarAssunto(String string) {
        if (string == null || string.isEmpty()) {
            throw new DadosInvalidosRascunhoException("Assunto vazio ou nullo");
        }
    }

    private void validarCorpo(String string) {
        if (string == null || string.isEmpty()) {
            throw new DadosInvalidosRascunhoException("Corpo vazio ou nullo");
        }
    }

    private void validarDestinatario(String emailDestinatario) {
        if (emailDestinatario == null || emailDestinatario.isEmpty()) {
            throw new UsuarioNaoEncontradoException("E-mail de destinatário vazio ou nullo");
        }

        Usuario usuario = usuarioService.buscarUsuarioPeloEmail(emailDestinatario);

        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("E-mail de destinatário: " + emailDestinatario + " não encontrado");
        }
    }
}
