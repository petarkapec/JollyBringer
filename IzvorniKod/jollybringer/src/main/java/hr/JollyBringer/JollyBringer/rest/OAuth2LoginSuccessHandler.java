package hr.JollyBringer.JollyBringer.rest;

import java.io.IOException;

import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {





    private final ParticipantService participantServiceJpa;

    public OAuth2LoginSuccessHandler(ParticipantService participantServiceJpa) {
        this.participantServiceJpa = participantServiceJpa;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        // Dohvatimo korisničke podatke
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");


        Participant user = participantServiceJpa.findByEmail(email);
        if (user == null) {
            participantServiceJpa.createParticipant(new Participant(name, email));
        }

        // Preusmjeravamo korisnika na stranicu nakon login-a
        response.sendRedirect("/dashboard");
    }
}
