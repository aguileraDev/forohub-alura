package com.alura.forohub.security;

import com.alura.forohub.dto.LoginDto;
import com.alura.forohub.dto.NewLoginDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    private final String secretKey;

    @Autowired
    public JwtService(@Value("${api.security.secret}") String secret){
        this.secretKey = secret;

    }

    public LoginDto generateJwtToken(NewLoginDto newLoginDto){
        String token="";
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            token = JWT.create()
                    .withIssuer("forohub")
                    .withClaim("id",newLoginDto.id())
                    .withSubject(newLoginDto.username())
                    .withExpiresAt(generateExpireDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            logger.error("Ocurrio un error al generar el jwt", exception.getMessage());
        }

        return new LoginDto(newLoginDto.id(), newLoginDto.username(), token, generateExpireDate().toString());
    }

    public String validateToken(String token){
        DecodedJWT verifier;
        String subject ="";
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            verifier = JWT.require(algorithm)
                    .withIssuer("forohub")
                    .build()
                    .verify(token);

            subject = verifier.getSubject();

            if (subject == null){
                throw new JWTVerificationException("Jwt token null");
            }

        } catch (NullPointerException | JWTVerificationException exception){
            String message = "invalid or null jwt token";
            logger.error(message);
        }

        return subject;
    }
    private Instant generateExpireDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}
