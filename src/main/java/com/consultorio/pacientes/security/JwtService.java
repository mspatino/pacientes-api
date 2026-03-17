package com.consultorio.pacientes.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    //Clase encargada de:crear JWT, leer JWT, validar JWT
    private static final String SECRET_KEY =
            "mi_clave_super_secreta_para_jwt_12345678901234567890";

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username) {
   //duracion del token 12 hs
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {

        try {

            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);

            return true;

        } catch (JwtException | IllegalArgumentException e) {

            return false;

        }
    }
}
