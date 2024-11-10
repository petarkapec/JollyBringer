package hr.JollyBringer.JollyBringer.rest;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

//TODO change loadByUsername
//TODO configure oauth2 and whats needed
@Service
public class ParticipantUserDetailsService implements UserDetailsService {
    @Value("${hr.JollyBringer.JollyBringer.admin.password}")
    private String adminPassHash;


    @Override
    public UserDetails loadUserByUsername(String username) {

        if ("admin".equals(username))
            return new User(
                    username,
                    adminPassHash,
                    commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
        else
            throw new UsernameNotFoundException("Unknown user: " + username);

    }
}
