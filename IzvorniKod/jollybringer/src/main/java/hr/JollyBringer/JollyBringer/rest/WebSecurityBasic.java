package hr.JollyBringer.JollyBringer.rest;

import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.domain.Role;
import hr.JollyBringer.JollyBringer.service.EntityMissingException;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import hr.JollyBringer.JollyBringer.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = false)
public class WebSecurityBasic {

    @Value("${progi.frontend.url}")
    private String frontendUrl;

    private final ParticipantService participantService;
    private final RoleService roleService;

    public WebSecurityBasic(ParticipantService participantService, RoleService roleService) {
        this.participantService = participantService;
        this.roleService = roleService;
    }

    @Bean
    @Profile("oauth-security")
    public SecurityFilterChain oauthFilterChain(HttpSecurity http) throws Exception {
        http
                // Omogući CORS za komunikaciju između različitih domena
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of(frontendUrl, "https://jollybringer-frontend-latest.onrender.com"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowCredentials(true); // Ovo omogućava slanje kolačića
                    config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
                    return config;
                }))
                // Isključi CSRF (po potrebi)
                .csrf(AbstractHttpConfigurer::disable)
                // Omogući autentifikaciju za specifične endpoint-e
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/check-auth").authenticated(); // /check-auth zahtijeva autentifikaciju
                    auth.anyRequest().authenticated(); // Sve ostalo također zahtijeva autentifikaciju
                })
                // Konfiguracija OAuth2 logina
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(this::oauth2AuthenticationSuccessHandler)
                        .userInfoEndpoint(userInfo -> userInfo.userAuthoritiesMapper(this.authorityMapper()))
                )
                // Konfiguracija logout funkcionalnosti
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                        })
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                )
                // Omogući sesije (cookie-based autentifikacija)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Kreiraj sesiju ako je potrebna
                )
                // Konfiguriraj izuzetke za neautorizirane zahtjeve
                .exceptionHandling(handling -> handling.authenticationEntryPoint(new Http403ForbiddenEntryPoint()));

        return http.build();
    }

    private void oauth2AuthenticationSuccessHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        if (oauthUser == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        // Spremi korisnika u bazu ako ne postoji
        if (participantService.findByEmail(email).isEmpty()) {
            Role participantRole = roleService.findByName("Participant")
                    .orElseThrow(() -> new EntityMissingException(Role.class, "Participant"));
            participantService.createParticipant(new Participant(name, email, participantRole));
        }

        // Redirect na frontend nakon uspješne prijave
        response.sendRedirect(frontendUrl + "/dashboard");
    }

    @Bean
    public GrantedAuthoritiesMapper authorityMapper() {
        SimpleAuthorityMapper authorityMapper = new SimpleAuthorityMapper();
        authorityMapper.setDefaultAuthority("ROLE_PARTICIPANT");
        return authorityMapper;
    }

    @Bean
    @Profile({ "basic-security", "form-security", "oauth-security" })
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain h2ConsoleSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher(PathRequest.toH2Console());
        http.csrf(AbstractHttpConfigurer::disable);
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        return http.build();
    }
}
