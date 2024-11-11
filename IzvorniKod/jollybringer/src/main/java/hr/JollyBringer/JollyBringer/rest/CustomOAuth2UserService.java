package hr.JollyBringer.JollyBringer.rest;

import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import hr.JollyBringer.JollyBringer.service.impl.ParticipantServiceJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

//@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {



    private final ParticipantService participantServiceJpa;

    public CustomOAuth2UserService(ParticipantService participantServiceJpa) {
        this.participantServiceJpa = participantServiceJpa;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        
        String email = (String) oAuth2User.getAttributes().get("email");
        String name = (String) oAuth2User.getAttributes().get("name");
        
        // Provjeri postoji li korisnik i pohrani ga ako ne postoji
        Participant user = participantServiceJpa.findByEmail(email);
        if (user == null) {
            participantServiceJpa.createParticipant(new Participant(name, email));
        }
        
        return oAuth2User;
    }
}

