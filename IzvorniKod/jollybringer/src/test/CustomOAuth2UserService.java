package hr.JollyBringer.JollyBringer.rest;

import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {



    private final ParticipantService participantServiceJpa;

    public CustomOAuth2UserService(ParticipantService participantServiceJpa) {
        this.participantServiceJpa = participantServiceJpa;
    }


    //TODO postavljanje rola
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        
        String email = (String) oAuth2User.getAttributes().get("email");
        String name = (String) oAuth2User.getAttributes().get("name");
        
        // Provjeri postoji li korisnik i pohrani ga ako ne postoji
        Optional<Participant> user = participantServiceJpa.findByUsername(name);
        if (user.isEmpty()) {
            participantServiceJpa.createParticipant(new Participant(name, email));
        }
        
        return oAuth2User;
    }
}

