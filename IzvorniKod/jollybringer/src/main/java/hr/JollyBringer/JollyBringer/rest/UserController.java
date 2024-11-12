package hr.JollyBringer.JollyBringer.rest;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile({"form-security", "oauth-security"})
@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @GetMapping
    public User getCurrentUser(@AuthenticationPrincipal User user) {
        return user;
    }
}
