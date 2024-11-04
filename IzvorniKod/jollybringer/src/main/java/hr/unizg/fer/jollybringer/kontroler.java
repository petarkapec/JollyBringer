package hr.unizg.fer.jollybringer;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Dopušta zahtjeve s React servera
public class kontroler {

    @GetMapping("/")
    public String helloWorld() {
        return "Hello, World!";
    }
}