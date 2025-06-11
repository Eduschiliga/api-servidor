package com.eduardo.apiservidor.service.usuario;

import com.eduardo.apiservidor.entity.usuario.Usuario;
import com.eduardo.apiservidor.exception.customizadas.padrao.FalhaProcessamentoException;
import com.eduardo.apiservidor.exception.customizadas.usuario.UsuarioNaoEncontradoException;
import com.eduardo.apiservidor.mapper.UsuarioMapper;
import com.eduardo.apiservidor.model.dto.mensagem.MensagemSucessoDTO;
import com.eduardo.apiservidor.model.dto.usuario.AtualizarUsuarioDTO;
import com.eduardo.apiservidor.model.dto.usuario.CriacaoUsuarioDTO;
import com.eduardo.apiservidor.model.dto.usuario.RetornoUsuarioDTO;
import com.eduardo.apiservidor.model.dto.usuario.UsuarioDTO;
import com.eduardo.apiservidor.repository.usuario.UsuarioRepository;
import com.eduardo.apiservidor.service.auth.AuthService;
import com.eduardo.apiservidor.service.validacao.UsuarioValidacaoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioValidacaoService usuarioValidacaoService;

    @Transactional
    public MensagemSucessoDTO salvar(CriacaoUsuarioDTO criacaoUsuarioDTO) {
        log.info("Salvando usuário: {}", criacaoUsuarioDTO);
        usuarioValidacaoService.validarUsuario(criacaoUsuarioDTO);
        log.info("Dados de usuário válido: {}", criacaoUsuarioDTO);

        salvarUsuario(criacaoUsuarioDTO);

        return new MensagemSucessoDTO("Sucesso ao cadastrar usuario");
    }

    @Transactional
    protected void salvarUsuario(CriacaoUsuarioDTO criacaoUsuarioDTO) {
        try {
            Usuario usuario = usuarioMapper.usuarioCriacaoDTOtoUsuario(criacaoUsuarioDTO);

            usuario.setUsuarioId(null);
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

            usuario = usuarioRepository.save(usuario);
            log.info("Usuário salvo com sucesso: {}", usuario);
        } catch (Exception e) {
            throw new FalhaProcessamentoException("Erro ao processar usuário:" + e.getMessage());
        }
    }


    public RetornoUsuarioDTO buscarUsuarioPeloToken(String token) {
        log.info("Buscando Usuário do Token: {}", token);
        Usuario usuario = authService.findUsuarioEntityByToken(token);

        log.info("Usuário encontrado: {}", usuario);
        UsuarioDTO usuarioDTO = usuarioMapper.usuarioToUsuarioDTO(usuario);

        return RetornoUsuarioDTO.builder()
                .mensagem("Sucesso ao buscar usuario")
                .usuario(usuarioDTO)
                .build();
    }


    @Transactional
    public RetornoUsuarioDTO atualizar(String token, AtualizarUsuarioDTO atualizarUsuarioDto) {
        log.info("Atualizando Usuário do Token: {}", token);
        log.info("Buscando Usuário do Token: {}", token);
        Usuario usuario = authService.findUsuarioEntityByToken(token);

        log.info("Validando dados para atualizar");
        usuarioValidacaoService.validarNome(atualizarUsuarioDto.getNome());
        usuarioValidacaoService.validarSenha(atualizarUsuarioDto.getSenha());
        log.info("Dados válidos: {}", atualizarUsuarioDto);

        log.info("Atualizando dados do Usuario: {}", usuario);
        usuario.setNome(atualizarUsuarioDto.getNome());
        usuario.setSenha(passwordEncoder.encode(atualizarUsuarioDto.getSenha()));
        usuario = usuarioRepository.save(usuario);
        log.info("Dados atualizados do Usuario: {}", usuario);

        UsuarioDTO usuarioDTO = usuarioMapper.usuarioToUsuarioDTO(usuario);

        return RetornoUsuarioDTO.builder()
                .mensagem("Sucesso atualizar usuario")
                .usuario(usuarioDTO)
                .build();
    }

    @Transactional
    public void deletar(String token) {
        log.info("Deletar usuário do Token: {}", token);
        Usuario usuario = authService.findUsuarioEntityByToken(token);

        usuarioRepository.delete(usuario);
        log.info("Usuário deletado com sucesso");
    }

    public Usuario buscarUsuarioPeloEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }
}
