package hr.JollyBringer.JollyBringer.rest;


import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//TODO change loadByUsername
//TODO configure oauth2 and whats needed

//TODO saving password from user and assigning it to them
@Profile({ "oauth-security"})
@Service
public class ParticipantUserDetailsService implements UserDetailsService {
    @Value("${hr.JollyBringer.JollyBringer.admin.password}")
    private String adminPassHash;

    private final ParticipantService participantService;

    public ParticipantUserDetailsService(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Find the participant by username
        Participant participant = participantService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + username));

        // Return a UserDetails object containing username, password, and roles
        return new User(participant.getUsername(), adminPassHash, authorities(participant));
    }



    private List<GrantedAuthority> authorities(Participant participant) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (participant.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (participant.isPresident()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_LEAD"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_PARTICIPANT"));
        }
        System.out.println("ASSIGNED ROLE FROM USER SERVICE");
        return authorities;
    }
}
