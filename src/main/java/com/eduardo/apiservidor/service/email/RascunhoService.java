package com.eduardo.apiservidor.service.email;

import com.eduardo.apiservidor.entity.email.Email;
import com.eduardo.apiservidor.entity.usuario.Usuario;
import com.eduardo.apiservidor.mapper.EmailMapper;
import com.eduardo.apiservidor.model.dto.email.EmailCriacaoDTO;
import com.eduardo.apiservidor.model.dto.email.rascunho.RascunhoDTO;
import com.eduardo.apiservidor.model.dto.mensagem.CriacaoRascunhoDTO;
import com.eduardo.apiservidor.model.enums.StatusEmail;
import com.eduardo.apiservidor.repository.email.EmailRepository;
import com.eduardo.apiservidor.service.auth.AuthService;
import com.eduardo.apiservidor.service.usuario.UsuarioService;
import com.eduardo.apiservidor.service.validacao.EmailValidacaoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RascunhoService {
    private final EmailRepository emailRepository;
    private final UsuarioService usuarioService;
    private final EmailMapper emailMapper;
    private final AuthService authService;
    private final EmailValidacaoService emailValidacaoService;

    @Transactional
    public CriacaoRascunhoDTO criar(EmailCriacaoDTO emailCriacaoDTO, String token) {
        Usuario usuario = authService.findUsuarioEntityByToken(token);

        emailValidacaoService.validarEmail(emailCriacaoDTO);

        Email email = emailMapper.criacaoDtoToEmailEntity(emailCriacaoDTO);
        email.setEmailRemetente(usuario.getEmail());
        email.setStatus(StatusEmail.RASCUNHO);

        email = emailRepository.save(email);
        log.info("Rascunho criado com sucesso: {}", email);

        RascunhoDTO rascunhoDTO = emailMapper.entityToRascunhoDto(email);
        return new CriacaoRascunhoDTO("Rascunho criado", rascunhoDTO);
    }

}
