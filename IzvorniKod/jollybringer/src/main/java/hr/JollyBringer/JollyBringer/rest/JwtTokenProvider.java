package hr.JollyBringer.JollyBringer.rest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class JwtTokenProvider {
    private final String secretKey = "7d89d8a595f752c6634656b5e4b0ea73716b161c46f4ffd550819e3bc87be3858949a84ea6ec45ae4d5da66bb237ce2fd2f29e45788d3ac5a4d9493359457002aae2ab5f3e57c0d430c513bb49ee5f4f4123bb0e2428ab8413291e988cd9ca7197b55e6d1c6bbc0257fd1f94a98486641986afad8db9a3d1463b5011812a1c7650c65591d486444b1cc8143737b9b9cb468459bcbd87941d805735c3734a41118bf2c753cbd5c89c1a21b5ecd145a5d23bf4061be23ba528262b8123d5c2048c709146181ba917f7b3cf4c995ea998edb32639222469aebfbd55ae3d0548945452a55996849a0770b8fec0cdb7af81599d80e615c9bac730185ca6711771cd1fb4e0207484b680b400e47769bf7435b310c962beac798928ac9a1b092546e1b9750dde86d0869aafbc6f7f5fe155a82b8c54dd3cc666d067bcbac49849d6b801";

    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (SignatureException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}
