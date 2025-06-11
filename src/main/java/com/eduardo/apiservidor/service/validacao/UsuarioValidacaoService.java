package com.eduardo.apiservidor.service.validacao;

import com.eduardo.apiservidor.entity.usuario.Usuario;
import com.eduardo.apiservidor.model.dto.usuario.CriacaoUsuarioDTO;
import com.eduardo.apiservidor.repository.usuario.UsuarioRepository;
import com.eduardo.apiservidor.util.EmailUtil;
import com.eduardo.apiservidor.exception.customizadas.usuario.DadosInvalidosException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioValidacaoService {
    private final UsuarioRepository usuarioRepository;

    public void validarUsuario(CriacaoUsuarioDTO criacaoUsuarioDTO) {
        log.info("Validando dados do Usuário: {}", criacaoUsuarioDTO);
        validarNome(criacaoUsuarioDTO.getNome());
        validarSenha(criacaoUsuarioDTO.getSenha());
        validarEmail(criacaoUsuarioDTO.getEmail());
    }

    public void validarSenha(String senha) {
        if (senha == null || senha.isEmpty()) {
            throw new DadosInvalidosException("Senha de usuário não pode ser nulla e nem vazia");
        }

        if (senha.length() < 8 || senha.length() > 20) {
            throw new DadosInvalidosException("Senha do usuário não pode ser maior do que 20 e menor que 8 caracteres");
        }
    }

    public void validarNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new DadosInvalidosException("Nome de usuário não pode ser nullo e nem vazio");
        }

        if (nome.length() > 255) {
            throw new DadosInvalidosException("Nome de usuário não pode ser maior que 255 caracteres");
        }
    }

    public void validarEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);

        if (usuario == null) {
            if (!EmailUtil.isValidEmail(email)) {
                throw new DadosInvalidosException("E-mail inválido");
            }
        } else {
            throw new DadosInvalidosException("E-mail já cadastrado");
        }
    }
}
