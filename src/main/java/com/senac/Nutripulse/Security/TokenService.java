package com.senac.Nutripulse.Security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.senac.Nutripulse.Entity.Users;

@Service
public class TokenService {

    @Value("{api.security.token.secret}")
    private String secret;
    
    public String generateToken(Users users){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                            .withIssuer("dice & rise")
                            .withSubject(users.getEmail())
                            .withExpiresAt(genExpirationDate())
                            .sign(algorithm);
            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generation token", e);
        }
    }

    public String valideToken (String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("dice & rise")
                    .build()
                    .verify(token)
                    .getSubject();

        }catch (JWTVerificationException e){
            return "";
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
