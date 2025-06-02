package com.eduardo.apiservidor.config.security;

import com.eduardo.apiservidor.entity.Usuario;
import com.eduardo.apiservidor.repository.UsuarioRepository;
import com.eduardo.apiservidor.service.TokenService;
import exception.customizadas.jwt.TokenJWTException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            try {
                if (tokenService.isTokenExpirado(tokenJWT)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token expirado.");
                    throw new TokenJWTException("Token expirado.");
                }

                String subject = tokenService.getSubject(tokenJWT);

                Usuario usuario = usuarioRepository.findByEmail(subject)
                        .orElseThrow(()-> new TokenJWTException("Usuário associado ao token não encontrado."));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                throw new TokenJWTException("Token inválido: " + e.getMessage());
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
