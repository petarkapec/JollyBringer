package hr.JollyBringer.JollyBringer.rest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serial;

@RestController
@RequestMapping("")
//@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {

    @GetMapping("/home")
    String index(){
        return "Home here";
    }

    @GetMapping("/secured")
    @Secured("ROLE_USER")
    String secured(){
        return "This is secured";
    }

    @GetMapping("/dashboard")
    String dashboard(){
        return "";
    }

    @GetMapping("/login")
    String login(){
        return "";
    }

    @GetMapping("/auth/google")
    String loginGoogle(){
        return "";
    }
}
