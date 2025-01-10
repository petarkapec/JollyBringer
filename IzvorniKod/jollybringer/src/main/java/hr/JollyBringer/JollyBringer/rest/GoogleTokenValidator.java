package hr.JollyBringer.JollyBringer.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Base64;

@Component
public class GoogleTokenValidator {

    private final GooglePublicKeysProvider publicKeysProvider;

    public GoogleTokenValidator(GooglePublicKeysProvider publicKeysProvider) {
        this.publicKeysProvider = publicKeysProvider;
    }

    public Claims validateToken(String token) {
        try {
            // Parsiraj header kako bismo dobili "kid"
            String[] parts = token.split("\\.");
            String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
            String kid = new ObjectMapper().readTree(headerJson).get("kid").asText();

            // Preuzmi ključ za validaciju
            PublicKey publicKey = publicKeysProvider.getPublicKey(kid);
            if (publicKey == null) {
                throw new RuntimeException("Public key not found for kid: " + kid);
            }

            // Validiraj token
            return Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Token validation failed", e);
        }
    }
}

