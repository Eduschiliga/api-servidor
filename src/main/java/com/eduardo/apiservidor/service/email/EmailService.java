package com.eduardo.apiservidor.service.email;

import com.eduardo.apiservidor.entity.email.Email;
import com.eduardo.apiservidor.entity.usuario.Usuario;
import com.eduardo.apiservidor.exception.customizadas.email.EmailNaoEncontradoException;
import com.eduardo.apiservidor.mapper.EmailMapper;
import com.eduardo.apiservidor.model.dto.email.EmailCriacaoDTO;
import com.eduardo.apiservidor.model.dto.email.rascunho.CriacaoRascunhoDTO;
import com.eduardo.apiservidor.model.dto.email.rascunho.RascunhoDTO;
import com.eduardo.apiservidor.model.dto.email.rascunho.RascunhoListaDTO;
import com.eduardo.apiservidor.model.dto.mensagem.MensagemSucessoDTO;
import com.eduardo.apiservidor.model.enums.StatusEmail;
import com.eduardo.apiservidor.repository.email.EmailRepository;
import com.eduardo.apiservidor.service.auth.AuthService;
import com.eduardo.apiservidor.service.validacao.EmailValidacaoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;
    private final EmailMapper emailMapper;
    private final AuthService authService;
    private final EmailValidacaoService emailValidacaoService;

    @Transactional
    public CriacaoRascunhoDTO criarRascunho(EmailCriacaoDTO rascunhoDto, String token) {
        log.info("Iniciando processo de criação de rascunho.");

        emailValidacaoService.validarEmail(rascunhoDto);

        Usuario usuario = authService.findUsuarioEntityByToken(token);

        Email email = emailMapper.criacaoDtoToEmailEntity(rascunhoDto);
        email.setEmailRemetente(usuario.getEmail());
        email.setStatus(StatusEmail.RASCUNHO);

        Email emailSalvo = emailRepository.save(email);
        log.info("Rascunho ID {} criado com sucesso para o usuário {}.", emailSalvo.getEmailId(), usuario.getEmail());

        return criarRespostaRascunho(emailSalvo, "Rascunho criado com sucesso");
    }

    @Transactional
    public CriacaoRascunhoDTO atualizarRascunho(RascunhoDTO rascunhoDTO, String token) {
        log.info("Iniciando processo de atualização de rascunho ID: {}", rascunhoDTO.getRascunhoId());

        Email emailParaAtualizar = buscarRascunhoDoUsuarioPorId(rascunhoDTO.getRascunhoId(), token);

        emailValidacaoService.validarDestinatario(rascunhoDTO.getEmailDestinatario());

        atualizarCamposDoRascunho(emailParaAtualizar, rascunhoDTO);

        Email emailSalvo = emailRepository.save(emailParaAtualizar);
        log.info("Rascunho ID {} atualizado com sucesso.", emailSalvo.getEmailId());

        return criarRespostaRascunho(emailSalvo, "Rascunho atualizado com sucesso");
    }

    public RascunhoListaDTO buscarTodosRascunhos(String token) {
        Usuario usuario = authService.findUsuarioEntityByToken(token);
        log.info("Buscando todos os rascunhos para o usuário: {}", usuario.getEmail());

        List<Email> emails = emailRepository.buscarRascunhos(usuario.getEmail());
        List<RascunhoDTO> rascunhosDto = emailMapper.entityToRascunhoDtoList(emails);

        return new RascunhoListaDTO("Rascunhos listados com sucesso", rascunhosDto);
    }

    public CriacaoRascunhoDTO buscarRascunhoPorId(Long rascunhoId, String token) {
        log.info("Buscando rascunho por ID: {}", rascunhoId);
        Email email = buscarRascunhoDoUsuarioPorId(rascunhoId, token);
        return criarRespostaRascunho(email, "Rascunho encontrado com sucesso");
    }

    @Transactional
    public MensagemSucessoDTO deletarRascunhoPorId(Long rascunhoId, String token) {
        log.info("Iniciando processo de deleção do rascunho ID: {}", rascunhoId);
        Email emailParaDeletar = buscarRascunhoDoUsuarioPorId(rascunhoId, token);

        emailRepository.delete(emailParaDeletar);
        log.info("Rascunho ID {} deletado com sucesso.", rascunhoId);

        return new MensagemSucessoDTO("Rascunho #" + rascunhoId + " deletado com sucesso");
    }

    private Email buscarEmailPorId(Long emailId) {
        return emailRepository.findById(emailId)
                .orElseThrow(() -> new EmailNaoEncontradoException("E-mail não encontrado para o Id: " + emailId));
    }

    private Email buscarRascunhoDoUsuarioPorId(Long rascunhoId, String token) {
        Usuario usuario = authService.findUsuarioEntityByToken(token);
        Email email = buscarEmailPorId(rascunhoId);
        emailValidacaoService.validarSeRascunhoEDoUsuario(email, usuario.getEmail());
        log.info("Acesso ao rascunho ID {} validado para o usuário {}", rascunhoId, usuario.getEmail());
        return email;
    }

    private void atualizarCamposDoRascunho(Email email, RascunhoDTO rascunhoDTO) {
        email.setAssunto(rascunhoDTO.getAssunto());
        email.setCorpo(rascunhoDTO.getCorpo());
        email.setEmailDestinatario(rascunhoDTO.getEmailDestinatario());
    }

    private CriacaoRascunhoDTO criarRespostaRascunho(Email email, String mensagem) {
        RascunhoDTO rascunhoDto = emailMapper.entityToRascunhoDto(email);
        return new CriacaoRascunhoDTO(mensagem, rascunhoDto);
    }
}