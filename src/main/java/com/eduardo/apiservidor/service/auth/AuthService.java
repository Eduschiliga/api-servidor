package com.eduardo.apiservidor.service.auth;

import com.eduardo.apiservidor.entity.usuario.Usuario;
import com.eduardo.apiservidor.exception.customizadas.usuario.UsuarioNaoEncontradoException;
import com.eduardo.apiservidor.model.request.LoginRequest;
import com.eduardo.apiservidor.repository.usuario.UsuarioRepository;
import com.eduardo.apiservidor.service.jwt.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;
    private final ApplicationContext applicationContext;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public Usuario findUsuarioEntityByToken(String token) {
        log.info("Iniciando busca de usuário por token.");

        String tokenTratado = token.replace("Bearer ", "");
        log.info("Extraindo 'subject' (email) do token.");
        String subject = tokenService.getSubject(tokenTratado);
        log.info("Subject extraído com sucesso: {}", subject);

        log.info("Buscando usuário no banco de dados com o email: {}", subject);
        Usuario usuario = usuarioRepository.findByEmail(subject)
                .orElseThrow(() -> {
                    log.error("Falha ao buscar usuário. Nenhum usuário encontrado com o email: {}", subject);
                    return new UsuarioNaoEncontradoException("Não foi possível encontrar o usuário: " + subject);
                });

        log.info("Usuário encontrado com sucesso: {}", usuario.getEmail());
        return usuario;
    }

    public String authLogin(@Valid LoginRequest loginRequest) {
        log.info("Iniciando processo de login para o email: {}", loginRequest.getEmail());

        UsernamePasswordAuthenticationToken usernamePassAuthToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getSenha()
        );

        AuthenticationManager authenticationManager = applicationContext.getBean(AuthenticationManager.class);

        log.info("Autenticando credenciais para o email: {}", loginRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate(usernamePassAuthToken);
        log.info("Usuário autenticado com sucesso.");

        log.info("Gerando token JWT para o usuário: {}", ((Usuario) authentication.getPrincipal()).getEmail());
        String token = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        log.info("Token gerado com sucesso: {}", token);

        return token;
    }
}


