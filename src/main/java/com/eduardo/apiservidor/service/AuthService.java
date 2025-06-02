package com.eduardo.apiservidor.service;

import com.eduardo.apiservidor.entity.Usuario;
import com.eduardo.apiservidor.mapper.UsuarioMapper;
import com.eduardo.apiservidor.model.dto.usuario.UsuarioResponseDTO;
import com.eduardo.apiservidor.model.request.LoginRequest;
import com.eduardo.apiservidor.repository.UsuarioRepository;
import exception.customizadas.usuario.UsuarioNaoEncontradoException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final TokenService tokenService;
    private final ApplicationContext applicationContext;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public Usuario findUsuarioEntityByToken(String token) {
        token = token.replace("Bearer ", "");
        String subject = tokenService.getSubject(token);

        return usuarioRepository.findByEmail(subject)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Não foi possível encontrar o usuário: " + subject));
    }

    public UsuarioResponseDTO findUsuarioByToken(String token) {
        token = token.replace("Bearer ", "");
        String subject = tokenService.getSubject(token);
        Usuario usuario = usuarioRepository.findByEmail(subject)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Não foi possível encontrar o usuário: " + subject));

        return usuarioMapper.usuarioToUsuarioResponseDTO(usuario);
    }

    public String authLogin(@Valid LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken usernamePassAuthToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getSenha()
        );

        AuthenticationManager authenticationManager = applicationContext.getBean(AuthenticationManager.class);

        Authentication authentication = authenticationManager.authenticate(usernamePassAuthToken);

        return tokenService.gerarToken((Usuario) authentication.getPrincipal());
    }
}


