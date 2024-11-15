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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Profile({ "oauth-security"})
@RestController
@RequestMapping("/check-auth")
public class AuthCheckController
{
    private final ParticipantService participantService;
    private final ParticipantGroupService groupService;

    public AuthCheckController(ParticipantService participantService, ParticipantGroupService groupService) {
        this.participantService = participantService;
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<?> checkAuth( @AuthenticationPrincipal OidcUser oidcUser, Authentication authentication) {
        Optional<Participant> participant = participantService.findByEmail(oidcUser.getAttribute("email"));

        if (participant.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("isAuthenticated", authentication != null && authentication.isAuthenticated());
        response.put("role_id", participant.get().getRole().getId());
        response.put("role", participant.get().getRole().getName());
        response.put("username", participant.get().getUsername());
        response.put("email", participant.get().getEmail());
        response.put("user_id", participant.get().getId());
        response.put("groups", groupService.findById(participant.get().getId()).stream().map(ParticipantGroup::getName).toList());
        return ResponseEntity.ok(response);
    }
}
