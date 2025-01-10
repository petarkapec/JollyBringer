package hr.JollyBringer.JollyBringer.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class GooglePublicKeysProvider {

    private static final String GOOGLE_KEYS_URL = "https://www.googleapis.com/oauth2/v3/certs";
    private Map<String, PublicKey> publicKeysCache = new HashMap<>();

    @Scheduled(fixedRate = 3600000) // osvježavanje svakih 1 sat
    public void fetchGooglePublicKeys() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(GOOGLE_KEYS_URL, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode keys = objectMapper.readTree(response.getBody()).get("keys");

            Map<String, PublicKey> newKeys = new HashMap<>();
            for (JsonNode key : keys) {
                String kid = key.get("kid").asText();
                String modulus = key.get("n").asText();
                String exponent = key.get("e").asText();

                BigInteger n = new BigInteger(1, Base64.getUrlDecoder().decode(modulus));
                BigInteger e = new BigInteger(1, Base64.getUrlDecoder().decode(exponent));

                RSAPublicKeySpec spec = new RSAPublicKeySpec(n, e);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PublicKey publicKey = keyFactory.generatePublic(spec);

                newKeys.put(kid, publicKey);
            }

            this.publicKeysCache = newKeys;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch Google public keys", e);
        }
    }

    public PublicKey getPublicKey(String kid) {
        return publicKeysCache.get(kid);
    }
}
