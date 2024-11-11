package hr.JollyBringer.JollyBringer.rest;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import hr.JollyBringer.JollyBringer.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public OAuth2LoginSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        // Dohvatimo korisničke podatke
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        // Kreiramo korisnika i pohranjujemo ga u bazu
        User user = new User();
        user.setEmail(email);
        user.setName(name);

        // Spremamo korisnika u bazu
        userRepository.save(user);

        // Preusmjeravamo korisnika na stranicu nakon login-a
        response.sendRedirect("/dashboard");
    }
}
