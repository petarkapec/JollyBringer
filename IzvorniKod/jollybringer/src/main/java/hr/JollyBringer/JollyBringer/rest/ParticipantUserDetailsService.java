package hr.JollyBringer.JollyBringer.rest;


import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

//TODO change loadByUsername
//TODO configure oauth2 and whats needed

//TODO saving password from user and assigning it to them
//@Service
public class ParticipantUserDetailsService implements UserDetailsService {
    @Value("${hr.JollyBringer.JollyBringer.admin.password}")
    private String adminPassHash;

    private final ParticipantService participantService;

    public ParticipantUserDetailsService(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return new User(username, adminPassHash, authorities(username));
    }



    public List<GrantedAuthority> authorities(String username) {

        if ("admin".equals(username))
            return commaSeparatedStringToAuthorityList("ROLE_ADMIN");
        Participant participant = participantService.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("No user '" + username + "'")
        );
        if (participant.isPresident())
            return commaSeparatedStringToAuthorityList("ROLE_LEAD, ROLE_PARTICIPANT");
        else
            return commaSeparatedStringToAuthorityList("ROLE_PARTICIPANT");

    }
}
