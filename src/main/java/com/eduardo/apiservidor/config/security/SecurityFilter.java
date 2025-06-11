package com.eduardo.apiservidor.config.security;

import com.eduardo.apiservidor.entity.usuario.Usuario;
import com.eduardo.apiservidor.exception.customizadas.jwt.TokenJWTException;
import com.eduardo.apiservidor.repository.usuario.UsuarioRepository;
import com.eduardo.apiservidor.service.jwt.ListaPretaTokenService;
import com.eduardo.apiservidor.service.jwt.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
        private final ListaPretaTokenService listaPretaTokenService;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            try {
                log.info("Verificando se o token não está na lista de tokens inválidos");
                if (listaPretaTokenService.isTokenInvalidado(tokenJWT)) {
                    throw new TokenJWTException("Token invalidado");
                }
                log.info("Verificando se o token não está espirado");

                if (tokenService.isTokenExpirado(tokenJWT)) {
                    throw new TokenJWTException("Token expirado.");
                }

                log.info("Token válido");

                String subject = tokenService.getSubject(tokenJWT);

                Usuario usuario = usuarioRepository.findByEmail(subject)
                        .orElseThrow(() -> new TokenJWTException("Usuário associado ao token não encontrado."));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (TokenJWTException e) {
                SecurityContextHolder.clearContext();
                throw e;
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                throw new TokenJWTException("Token inválido ou erro de processamento: " + e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null) {
            return token.replace("Bearer ", "");
        }
        return null;
    }
}
