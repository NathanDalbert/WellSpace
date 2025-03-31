package com.WellSpace.Security;
import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var token = this.recoverToken(request);

        if (token != null) {
            DecodedJWT auth = this.tokenService.validateToken(token);

            // Verifique se a chave "UsuarioRole" é uma String
            String role = auth.getClaim("UsuarioRole").asString();

            if (role != null) {
                // Caso seja uma única role, cria um único GrantedAuthority
                var grants = List.of(new SimpleGrantedAuthority("ROLE_" + role));

                request.setAttribute("user_id", auth.getSubject());

                var authentication = new UsernamePasswordAuthenticationToken(auth.getSubject(), null, grants);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }


    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isBlank()) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}
