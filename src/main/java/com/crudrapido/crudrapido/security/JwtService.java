package com.crudrapido.crudrapido.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

// esta clase se encarga de generar y validar los tokens jwt
@Service
public class JwtService {

    // guardamos la llave que se utiliza para firmar los tokens
    private final SecretKey key;

    // guardamos el tiempo que tarda en expirar el token
    private final long expirationMs;

    // recibimos el secreto y el tiempo de expiracion desde application.properties
    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.expiration-ms}") long expirationMs) {

        // creamos la llave de firma a partir del secreto
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        // asignamos el tiempo de expiracion
        this.expirationMs = expirationMs;
    }

    // crea un token con el usuario, la fecha de emision y la fecha de expiracion
    public String generateToken(String username) {

        // obtenemos la fecha actual
        Date now = new Date();

        // construimos el token agregando los datos del usuario y sus fechas
        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMs))
                .signWith(key)
                .compact();
    }

    // obtiene el nombre del usuario que viene dentro del token
    // si la firma es incorrecta o el token ya expiro, genera un error
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // revisa si el token es valido
    public boolean isValid(String token) {
        try {

            // intentamos obtener el usuario para comprobar el token
            extractUsername(token);

            // si no ocurre ningun error, regresamos verdadero
            return true;

        } catch (JwtException | IllegalArgumentException e) {

            // si ocurre algun error, regresamos falso
            return false;
        }
    }
}