package hr.JollyBringer.JollyBringer.rest;


import hr.JollyBringer.JollyBringer.domain.ApplicationRequest;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.domain.Role;
import hr.JollyBringer.JollyBringer.service.ApplicationService;
import hr.JollyBringer.JollyBringer.service.EntityMissingException;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import hr.JollyBringer.JollyBringer.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ParticipantService participantService;
    private final RoleService roleService;
    public ApplicationController(ApplicationService applicationService, ParticipantService participantService, RoleService roleService) {
        this.applicationService = applicationService;
        this.participantService = participantService;
        this.roleService = roleService;
    }

    @PostMapping("/apply")
    //@Secured("ROLE_PARTICIPANT")
    public ResponseEntity<ApplicationRequest> applyForPresident(@RequestBody ApplicationDTO aplicationRequest) {
        Participant user = participantService.findById(aplicationRequest.getUser_id())
                .orElseThrow(() -> new EntityMissingException(Participant.class, aplicationRequest.getUser_id()));

        ApplicationRequest saved = applicationService.createApplicationRequest(new ApplicationRequest(user, aplicationRequest.isApplied()));
        return ResponseEntity.created(URI.create("/applications/" + saved.getUser().getId())).body(saved);
    }

    @GetMapping("/applications")
    //@Secured("ROLE_ADMIN")
    public List<ApplicationRequest> listParticipants() {
        return applicationService.listAll();
    }

    @PostMapping("/approve")
    public ResponseEntity<ApplicationRequest> approveApplication(@RequestBody ApplicationDTO applicationRequest) {

        System.out.println("Attempting to approve application for user ID: " + applicationRequest.getUser_id());
        ApplicationRequest saved = applicationService.findByUserId(applicationRequest.getUser_id())
                .orElseThrow(() -> new EntityMissingException(ApplicationRequest.class, applicationRequest.getUser_id()));


        applicationService.deleteApplicationRequest(saved.getId());


        Participant user = participantService.findById(applicationRequest.getUser_id())
                .orElseThrow(() -> new EntityMissingException(Participant.class, applicationRequest.getUser_id()));



        Role presidentRole = roleService.findByName("President")
                .orElseThrow(() -> new EntityMissingException(Role.class, "President"));


        user.setRole(presidentRole);


        participantService.updateParticipant(user);


        Long userId = saved.getUser() != null ? saved.getUser().getId() : null;

        // Return response with a created URI if user ID is present
        return userId != null
                ? ResponseEntity.created(URI.create("/applications/" + userId)).body(saved)
                : ResponseEntity.ok(saved);
    }
}
