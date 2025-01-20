package hr.JollyBringer.JollyBringer.rest;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    private final String secretKey = "DAUfUDzXhjzRfYbi0Cl/1sSjkIfSUXGMOB/hkPbvD7tptM8nlzUEB3Q7rT6pB0T8+f3NRBvlUVxUbPqGTYebRA=="; // Koristite sigurnu vrijednost

    // Generiranje tokena (opcionalno, za backend generaciju)
    public String generateToken(String email, long expirationTime) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    // Validacija tokena
    public boolean validateToken(String token) {
        try {
            // Parsiranje i provjera tokena
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey) // Postavljanje tajnog ključa
                    .parseClaimsJws(token); // Parsira JWT

            // Provjera datuma isteka tokena
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            System.out.println("Token je istekao.");
        } catch (JwtException e) {
            System.out.println("Neispravan token.");
        }
        return false;
    }

    // Dohvaćanje email-a iz tokena
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}

