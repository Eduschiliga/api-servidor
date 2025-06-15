package com.eduardo.apiservidor.service.validacao;

import com.eduardo.apiservidor.entity.email.Email;
import com.eduardo.apiservidor.exception.customizadas.email.DadosInvalidosRascunhoException;
import com.eduardo.apiservidor.exception.customizadas.email.RemetenteInvalidoException;
import com.eduardo.apiservidor.exception.customizadas.usuario.UsuarioNaoEncontradoException;
import com.eduardo.apiservidor.model.dto.email.EmailCriacaoDTO;
import com.eduardo.apiservidor.service.usuario.UsuarioService;
import com.eduardo.apiservidor.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailValidacaoService {
    private UsuarioService usuarioService;

    public void validarSeRascunhoEDoUsuario(Email email, String emailUsuario) {
        if (!Objects.equals(email.getEmailRemetente(), emailUsuario)) {
            throw new RemetenteInvalidoException("Não é possível editar um e-mail de outro usuário");
        }
        log.info("Validação de posse do rascunho confirmada.");
    }

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

    public void validarDestinatario(String emailDestinatario) {
        if (emailDestinatario != null && !emailDestinatario.isEmpty() && !EmailUtil.isValidEmail(emailDestinatario)) {
            throw new DadosInvalidosRascunhoException("E-mail de destinatário ( " + emailDestinatario + " ) inválido");
        }
    }
}
