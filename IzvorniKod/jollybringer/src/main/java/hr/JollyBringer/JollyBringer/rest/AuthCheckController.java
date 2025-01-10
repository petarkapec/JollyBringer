package hr.JollyBringer.JollyBringer.rest;


import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.domain.ParticipantGroup;
import hr.JollyBringer.JollyBringer.service.ParticipantGroupService;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import io.jsonwebtoken.Claims;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Profile({"oauth-security"})
@RestController
@RequestMapping("/check-auth")
public class AuthCheckController {

    private final ParticipantService participantService;
    private final ParticipantGroupService groupService;
    private final GoogleTokenValidator tokenValidator;

    public AuthCheckController(ParticipantService participantService, ParticipantGroupService groupService, GoogleTokenValidator tokenValidator) {
        this.participantService = participantService;
        this.groupService = groupService;
        this.tokenValidator = tokenValidator;
    }

    @GetMapping
    public ResponseEntity<?> checkAuth(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Dohvati token iz headera
            if (!authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Authorization header");
            }
            String token = authorizationHeader.substring(7);

            // Validiraj token
            Claims claims = tokenValidator.validateToken(token);

            String email = claims.get("email", String.class);
            Optional<Participant> participant = participantService.findByEmail(email);

            if (participant.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }

            Map<String, Object> response = new HashMap<>();
            response.put("email", email);
            response.put("role_id", participant.get().getRole().getId());
            response.put("role", participant.get().getRole().getName());
            response.put("username", participant.get().getUsername());
            response.put("user_id", participant.get().getId());
            response.put("groups", groupService.findById(participant.get().getId()).stream().map(ParticipantGroup::getName).toList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: " + e.getMessage());
        }
    }
}

