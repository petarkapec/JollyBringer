package hr.JollyBringer.JollyBringer.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class DashboardController {

    @GetMapping("/home")
    String index(){
        return "Home here";
    }

    @GetMapping("/secured")
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
