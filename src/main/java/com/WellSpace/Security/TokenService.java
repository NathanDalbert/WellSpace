package com.WellSpace.Security;

import com.WellSpace.modules.usuario.domain.Usuario;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Usuario user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String role = user.getUsuarioRole().name();

            Date dataNascimento = Date.from(user.getDataNascimento().atStartOfDay(ZoneOffset.UTC).toInstant());

            return JWT.create()
                    .withIssuer("WellSpace")
                    .withSubject(user.getEmail())
                    .withClaim("usuarioId", user.getUsuarioId().toString())
                    .withClaim("nome", user.getNome())
                    .withClaim("fotoPerfil", user.getFotoPerfil())
                    .withClaim("integridade", user.getIntegridade())
                    .withClaim("dataNascimento", dataNascimento)
                    .withClaim("UsuarioRole", role)
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException("Error generating token", e);
        }
    }

    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("WellSpace")
                    .build()
                    .verify(token);
        } catch (JWTDecodeException e) {
            throw new RuntimeException("Error decoding the token: " + e.getMessage(), e);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid token: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error validating token: " + e.getMessage(), e);
        }
    }

    private Instant getExpirationDate() {
        return LocalDate.now().plusDays(7).atStartOfDay(ZoneOffset.UTC).toInstant();
    }

    public String decodeToken(String token) {
        token = token.replace("Bearer", "");

        Algorithm algorithm = Algorithm.HMAC256(secret);

        try {
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }
}
