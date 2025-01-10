package hr.JollyBringer.JollyBringer.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class jwtService {
    @Value("${progi.token.secret.key}")
    private final String SECRET_KEY = "kTdDFNGdSkdBoGWeH3ZKvkyH03ByHc3w";

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 sati
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
