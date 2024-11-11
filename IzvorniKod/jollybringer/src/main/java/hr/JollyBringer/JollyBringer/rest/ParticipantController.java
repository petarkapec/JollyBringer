package hr.JollyBringer.JollyBringer.rest;

import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;



@RestController
@RequestMapping("/participants")
@CrossOrigin(origins = "http://localhost:5173")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping("") //možda   @Secured("ROLE_ADMIN")
    public List<Participant> listParticipants() {
        return participantService.listAll();
    }

    @GetMapping("/{id}") //možda   @Secured("ROLE_ADMIN")
    public Participant getParticipant(@PathVariable("id") long id) {
        return participantService.fetch(id);
    }

    @PostMapping("")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Participant> createParticipant(@RequestBody Participant participant){
        Participant saved = participantService.createParticipant(participant);
        return ResponseEntity.created(URI.create("/participants/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Participant> updateParticipant(@PathVariable("id") long id, @RequestBody Participant participant){
        if (!participant.getId().equals(id))
            throw new IllegalArgumentException("Participant ID must be preserved");

        Participant saved = participantService.updateParticipant(participant);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/id")
    @Secured("ROLE_ADMIN")
    public Participant deleteParticipant(@PathVariable("id") long id){

        return  participantService.deleteParticipant(id);
    }



}
