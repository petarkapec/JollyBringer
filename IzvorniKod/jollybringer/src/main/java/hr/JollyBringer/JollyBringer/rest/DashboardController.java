package hr.JollyBringer.JollyBringer.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class DashboardController {

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
