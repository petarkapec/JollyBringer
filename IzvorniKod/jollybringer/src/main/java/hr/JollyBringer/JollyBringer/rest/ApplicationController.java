package hr.JollyBringer.JollyBringer.rest;


import hr.JollyBringer.JollyBringer.domain.ApplicationRequest;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/apply{id")
    @Secured("ROLE_PARTICIPANT")
    public ResponseEntity<ApplicationRequest> applyForPresident(@RequestBody ApplicationRequest aplicationRequest) {
        ApplicationRequest saved = applicationService.createApplicationRequest(aplicationRequest);
        return ResponseEntity.created(URI.create("/applications/" + saved.getUserId())).body(saved);
    }
}
