package com.WellSpace.security;

import com.WellSpace.Security.TokenService;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var token = this.recoverToken(request);

        if (token != null) {
            // Decode the token using the TokenService
            DecodedJWT auth = this.tokenService.validateToken(token);

            // Extract "UsuarioRole" claim, assuming it's a List
            var roles = auth.getClaim("UsuarioRole").asList(String.class);

            var grants = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .toList();

            // Set the user_id in request attribute (you can customize this part)
            request.setAttribute("user_id", auth.getSubject());

            // Create the authentication object
            var authentication = new UsernamePasswordAuthenticationToken(auth.getSubject(), null, grants);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continue the filter chain
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
