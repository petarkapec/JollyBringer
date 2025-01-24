package hr.JollyBringer.JollyBringer.rest;


import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.domain.ParticipantGroup;
import hr.JollyBringer.JollyBringer.service.ParticipantGroupService;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
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

@Profile({ "oauth-security"})
@RestController
@RequestMapping("/check-auth")
public class AuthCheckController {

    private final ParticipantService participantService;
    private final ParticipantGroupService groupService;
    private final JwtTokenUtil jwtTokenUtil; // Utility klasa za validaciju JWT-a

    public AuthCheckController(ParticipantService participantService, ParticipantGroupService groupService, JwtTokenUtil jwtTokenUtil) {
        this.participantService = participantService;
        this.groupService = groupService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping
    public ResponseEntity<?> checkAuth(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        // Provjera zaglavlja
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Authorization header is missing or invalid", "isAuthenticated", false));
        }

        String token = authHeader.substring(7); // Ukloni "Bearer " prefiks

        // Validacija i parsiranje tokena
        String email;
        try {
            email = jwtTokenUtil.getEmailFromToken(token); // Dohvati email iz tokena
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid token", "isAuthenticated", false));
        }

        // Dohvaćanje korisnika
        Optional<Participant> participantOpt = participantService.findByEmail(email);
        if (participantOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not found", "isAuthenticated", false));
        }

        Participant participant = participantOpt.get();

        // Gradnja odgovora
        Map<String, Object> response = new HashMap<>();
        response.put("isAuthenticated", true);
        response.put("role_id", participant.getRole().getId());
        response.put("role", participant.getRole().getName());
        response.put("username", participant.getUsername());
        response.put("email", participant.getEmail());
        response.put("user_id", participant.getId());
        response.put("groups", groupService.findById(participant.getId())
                .stream()
                .map(ParticipantGroup::getName)
                .toList());

        return ResponseEntity.ok(response);
    }
}

