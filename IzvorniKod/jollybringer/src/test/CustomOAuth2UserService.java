package hr.JollyBringer.JollyBringer.rest;

import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final ParticipantService participantService;

    public CustomOAuth2UserService(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String email = oauth2User.getAttribute("email");
        Optional<Participant> participantOpt = participantService.findByEmail(email);

        Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

        if (participantOpt.isPresent()) {
            Participant participant = participantOpt.get();
            String roleName = "ROLE_" + participant.getRole().getName().toUpperCase();
            mappedAuthorities.add(new SimpleGrantedAuthority(roleName));
        } else {
            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_PARTICIPANT"));
        }

        // Return a new OAuth2User with mapped authorities
        return new DefaultOAuth2User(mappedAuthorities, oauth2User.getAttributes(), "email");
    }
}
