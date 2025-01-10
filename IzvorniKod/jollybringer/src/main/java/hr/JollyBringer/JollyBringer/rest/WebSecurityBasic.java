package hr.JollyBringer.JollyBringer.rest;

import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.domain.Role;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import hr.JollyBringer.JollyBringer.service.RoleService;
import hr.JollyBringer.JollyBringer.service.impl.jwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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
<<<<<<< Updated upstream
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
=======
>>>>>>> Stashed changes
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.List;

import static java.io.IO.println;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = false)
public class WebSecurityBasic {


    @Value("${progi.frontend.url}")
    private String frontendUrl;

    private final ParticipantService participantService;
    private final RoleService roleService;
    private final GoogleTokenValidator googleTokenValidator; // Promjena - koristimo GoogleTokenValidator

<<<<<<< Updated upstream

    public WebSecurityBasic(ParticipantService participantService, RoleService roleService) {
=======
    public WebSecurityBasic(ParticipantService participantService, RoleService roleService, GoogleTokenValidator googleTokenValidator) {
>>>>>>> Stashed changes
        this.participantService = participantService;
        this.roleService = roleService;
        this.googleTokenValidator = googleTokenValidator; // Promjena
    }

    @Bean
<<<<<<< Updated upstream
    @Profile("basic-security")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
=======
    @Profile("oauth-security")
    public SecurityFilterChain oauthFilterChain(HttpSecurity http) throws Exception {
        http
                // Omogući CORS za komunikaciju između različitih domena
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of(frontendUrl,"https://accounts.google.com", "https://jollybringer-frontend-latest.onrender.com","http://localhost:5173"));
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

>>>>>>> Stashed changes
        return http.build();
    }

    @Bean
    @Profile("oauth-security")
    public SecurityFilterChain oauthFilterChain(HttpSecurity http) throws Exception {

        return http
                .cors(cors -> cors.configurationSource(request -> {
<<<<<<< Updated upstream
                    var config = new org.springframework.web.cors.CorsConfiguration();
                    config.setAllowedOrigins(List.of(frontendUrl)); // Update to your frontend URL
=======
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of(
                            frontendUrl,
                            "https://jollybringer-frontend-latest.onrender.com",
                            "https://a4f1-2a05-4f46-208-ca00-f13e-287-b755-ff5a.ngrok-free.app"
                    ));
>>>>>>> Stashed changes
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
                    return config;
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/check-auth").authenticated();
                    auth.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> {
                    oauth2
                            .userInfoEndpoint(
                                    userInfoEndpoint -> userInfoEndpoint.userAuthoritiesMapper(this.authorityMapper()))
                            .successHandler(
                                    this::oauth2AuthenticationSuccessHandler);
                })
                .logout(logout -> logout
                        .logoutUrl("/logout") // The URL to trigger logout
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // Customize the logout success behavior here
                            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                        })
                        .invalidateHttpSession(true) // Invalidate the session on logout
                        .clearAuthentication(true) // Clear authentication information
                )
                .exceptionHandling(handling -> handling.authenticationEntryPoint(new Http403ForbiddenEntryPoint()))
                .build();
    }


    private void oauth2AuthenticationSuccessHandler(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        // Extract the OAuth2User details
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        // You can retrieve user information like this
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");
        println("Authenticated user: " + name + " (" + email + ")");
        // Save the user details to your database

        //TODO roles need to be in database on startup
        if(participantService.findByEmail(email).isEmpty()){
            println("User doesn't exist, adding to base");
            if(roleService.findByName("Participant").isEmpty()){
                roleService.createRole(new Role(1L, "Participant"));
                roleService.createRole(new Role(2L, "Christmas president"));
                roleService.createRole(new Role(3L, "Admin"));
            }
            participantService.createParticipant(new Participant(name, email, roleService.findByName("Participant").get()));
        }

<<<<<<< Updated upstream
        httpServletResponse.sendRedirect(frontendUrl + "/dashboard");
=======
        // Validiraj Google OAuth2 token
        String token = oauthUser.getAttribute("id_token"); // Pretpostavljamo da id_token dolazi s OAuth2 autentifikacijom
        Claims claims = googleTokenValidator.validateToken(token);

        // Ako je token validan, možete pohraniti podatke o korisniku, kao što su email i ime

        // Postavi odgovor s potvrdom (ako je potrebno)
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"status\":\"authenticated\"}");
        response.setStatus(HttpServletResponse.SC_OK);
>>>>>>> Stashed changes
    }

    @Bean
    @Profile("form-security")
    public SecurityFilterChain spaFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                .anyRequest().authenticated());
        http.formLogin(configurer -> {
                    configurer.successHandler((request, response, authentication) ->
                                    response.setStatus(HttpStatus.NO_CONTENT.value())
                            )
                            .failureHandler(new SimpleUrlAuthenticationFailureHandler());
                }
        );
        http.exceptionHandling(configurer -> {
            final RequestMatcher matcher = new NegatedRequestMatcher(
                    new MediaTypeRequestMatcher(MediaType.TEXT_HTML));
            configurer
                    .defaultAuthenticationEntryPointFor((request, response, authException) -> {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    }, matcher);
        });
        http.logout(configurer -> configurer
                .logoutUrl("/logout")
                .logoutSuccessHandler((request, response, authentication) ->
                        response.setStatus(HttpStatus.NO_CONTENT.value())));
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    @Profile({ "basic-security", "form-security", "oauth-security" })
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain h2ConsoleSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher(PathRequest.toH2Console());
        http.csrf(AbstractHttpConfigurer::disable);
        http.headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        return http.build();
    }

    private GrantedAuthoritiesMapper authorityMapper() {
        final SimpleAuthorityMapper authorityMapper = new SimpleAuthorityMapper();

        authorityMapper.setDefaultAuthority("ROLE_ADMIN");

        return authorityMapper;
    }

}
