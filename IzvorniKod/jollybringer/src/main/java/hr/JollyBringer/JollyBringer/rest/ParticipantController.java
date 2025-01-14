package hr.JollyBringer.JollyBringer.rest;

import hr.JollyBringer.JollyBringer.domain.Activity;
import hr.JollyBringer.JollyBringer.domain.ChatMessage;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;



@RestController
@RequestMapping("/participants")
//@CrossOrigin(origins = "http://localhost:5173")
public class ParticipantController {

    private final ParticipantService participantService;
    private final FeedbackService feedbackService;
    private final ActivityService activityService;
    private final ChatMessageService chatMessageService;
    private final ParticipantGroupService participantGroupService;


    public ParticipantController(ParticipantService participantService, FeedbackService feedbackService, ActivityService activityService,
                                 ChatMessageService chatMessageService, ParticipantGroupService participantGroupService) {
        this.participantService = participantService;
        this.feedbackService = feedbackService;
        this.activityService = activityService;
        this.chatMessageService = chatMessageService;
        this.participantGroupService = participantGroupService;
    }

    @GetMapping("")
    //@Secured("ROLE_PARTICIPANT")//možda   @Secured("ROLE_ADMIN")
    public List<Participant> listParticipants() {
        return participantService.listAll();
    }

    @GetMapping("/only")
    //@Secured("ROLE_PARTICIPANT")//možda   @Secured("ROLE_ADMIN")
    public List<Participant> listOnlyParticipants() {
        return participantService.listAllWithRole("Participant");
    }

    @GetMapping("/{id}") //možda   @Secured("ROLE_ADMIN")
    public Participant getParticipant(@PathVariable("id") long id) {
        return participantService.fetch(id);
    }

    @PostMapping("")
    //@Secured("ROLE_ADMIN")
    public ResponseEntity<Participant> createParticipant(@RequestBody Participant participant){
        Participant saved = participantService.createParticipant(participant);
        return ResponseEntity.created(URI.create("/participants/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    //@Secured("ROLE_ADMIN")
    public ResponseEntity<Participant> updateParticipant(@PathVariable("id") long id, @RequestBody Participant participant){
        if (!participant.getId().equals(id))
            throw new IllegalArgumentException("Participant ID must be preserved");

        Participant saved = participantService.updateParticipant(participant);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    //@Secured("ROLE_ADMIN")
    public Participant deleteParticipant(@PathVariable("id") long id){
        List<Activity> activities = activityService.findByCreatedBy(participantService.fetch(id).getUsername());
        for (Activity activity : activities) {
            feedbackService.deleteRelatedFeedbacks(activity.getId());
            activityService.deleteActivity(activity.getId());
        }
        List<ChatMessage> messages = chatMessageService.findByParticipantId(participantService.fetch(id).getId());
        for(ChatMessage message : messages) {
            chatMessageService.deleteMessage(message.getId());
        }
        Participant deleted = participantService.fetch(id);
        if(participantGroupService.findByMember(deleted).isPresent()) {
            participantGroupService.removeMember(participantGroupService.findByMember(deleted).get().getId(), deleted.getId());
            return  participantService.deleteParticipant(id);
        } else {
            return  participantService.deleteParticipant(id);
        }

    }



}
