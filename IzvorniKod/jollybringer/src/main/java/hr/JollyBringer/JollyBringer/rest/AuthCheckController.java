package hr.JollyBringer.JollyBringer.rest;


import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Profile({ "oauth-security"})
@RestController
@RequestMapping("/check-auth")
public class AuthCheckController
{
    @GetMapping
    public ResponseEntity<?> checkAuth( Authentication authentication) {

        Map<String, Object> response = new HashMap<>();
        response.put("isAuthenticated", authentication != null && authentication.isAuthenticated());
        return ResponseEntity.ok(response);
    }
}
