package com.WellSpace.Security;

import com.WellSpace.modules.usuario.domain.Usuario;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
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

            // Convertendo a role para String (nome da role)
            String role = user.getUsuarioRole().name();  // Aqui estamos pegando o nome da role como string

            // Convertendo LocalDate para Date (para claims)
            Date dataNascimento = Date.from(user.getDataNascimento().atStartOfDay(ZoneOffset.UTC).toInstant());

            return JWT.create()
                    .withIssuer("WellSpace")
                    .withSubject(user.getEmail())
                    .withClaim("usuario_id", user.getUsuario_id().toString())
                    .withClaim("nome", user.getNome())
                    .withClaim("fotoPerfil", user.getFotoPerfil())
                    .withClaim("integridade", user.getIntegridade())
                    .withClaim("dataNascimento", dataNascimento)  // Convertendo LocalDate para Date
                    .withClaim("UsuarioRole", role)  // A role como uma String
                    .withExpiresAt(getExpirationDate())  // Usando o método de expiração
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
        } catch (Exception e) {
            throw new RuntimeException("Invalid token", e);
        }
    }

    private Instant getExpirationDate() {
        return LocalDate.now().plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();
    }

    public String decodeToken(String token) {
        token = token.replace("Bearer ", "");

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
