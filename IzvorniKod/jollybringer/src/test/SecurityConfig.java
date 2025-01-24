package hr.JollyBringer.JollyBringer.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> {
                             auth.requestMatchers("/login", "/login/oauth2/**", "/h2-console/**").permitAll(); // Allow access to login URL endpoints

                             auth.anyRequest().authenticated(); // All other requests need authentication

                    }


            )
            .oauth2Login(oauth2Login ->
                oauth2Login

                    .successHandler(this.oauth2SuccessHandler()) // Set custom handler for successful login
                    
            );

        return http.build();
    }

    //
    //private GrantedAuthoritiesMapper authorityMapper() {
    //    SimpleAuthorityMapper authorityMapper = new SimpleAuthorityMapper();
    //authorityMapper.setDefaultAuthority("ROLE_USER");
    //return authorityMapper;
    //}

    // Custom OAuth2 Success Handler
    private AuthenticationSuccessHandler oauth2SuccessHandler() {
        return (request, response, authentication) -> {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

            // Extract user data
            String email = oauth2User.getAttribute("email");
            String name = oauth2User.getAttribute("name");


            // You can save user data to the database or perform any other logic
            System.out.println("User logged in: " + name + " (" + email + ")");


            // Redirect user after successful login to a non-login page (example: /home)
            response.sendRedirect("/home");  // Redirect to your homepage or dashboard
        };
    }
}
