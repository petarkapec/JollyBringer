package hr.JollyBringer.JollyBringer.rest;

import hr.JollyBringer.JollyBringer.domain.*;
import hr.JollyBringer.JollyBringer.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/participants")
//@CrossOrigin(origins = "http://localhost:5173")
public class ParticipantController {

    private final ParticipantService participantService;
    private final FeedbackService feedbackService;
    private final ActivityService activityService;
    private final ChatMessageService chatMessageService;
    private final ParticipantGroupService participantGroupService;
    private final ApplicationService applicationService;


    public ParticipantController(ParticipantService participantService, FeedbackService feedbackService, ActivityService activityService,
                                 ChatMessageService chatMessageService, ParticipantGroupService participantGroupService, ApplicationService applicationService) {
        this.participantService = participantService;
        this.feedbackService = feedbackService;
        this.activityService = activityService;
        this.chatMessageService = chatMessageService;
        this.participantGroupService = participantGroupService;
        this.applicationService = applicationService;
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

        if (deleted.isPresident()) {
            List<ParticipantGroup> groups = participantGroupService.findByPresident(deleted);
            if (groups != null) {
                for (ParticipantGroup group : groups) {
                    Set<Participant> members = group.getMembers();

                    if (members.size() > 1) {
                        // Filtriramo člana koji nije jednak `deleted` i biramo slučajnog
                        List<Participant> eligibleMembers = members.stream()
                                .filter(member -> !member.equals(deleted))
                                .collect(Collectors.toList());

                        if (!eligibleMembers.isEmpty()) {
                            Participant newPresident = eligibleMembers.get(
                                    new Random().nextInt(eligibleMembers.size())); // Random izbor

                            newPresident.setRole(new Role(2L, "President"));
                            group.setPresident(newPresident);
                            participantService.updateParticipant(newPresident);
                        }
                    } else {
                        group.setPresident(null);
                        participantGroupService.deleteGroup(group.getId());
                    }
                }
            }
        }

        deleted.setRole(new Role(1L, "Participant"));
        participantService.updateParticipant(deleted);
        applicationService.deleteApplicationRequest(deleted.getId());
        if(participantGroupService.findByMember(deleted).isPresent()) {
            participantGroupService.removeMember(participantGroupService.findByMember(deleted).get().getId(), deleted.getId());
            return  participantService.deleteParticipant(id);
        } else {
            return  participantService.deleteParticipant(id);
        }

    }



}
