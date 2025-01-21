package hr.JollyBringer.JollyBringer.rest;

import hr.JollyBringer.JollyBringer.domain.Activity;
import hr.JollyBringer.JollyBringer.domain.Feedback;
import hr.JollyBringer.JollyBringer.service.ActivityService;
import hr.JollyBringer.JollyBringer.service.FeedbackService;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import hr.JollyBringer.JollyBringer.service.impl.ActivityServiceJPA;
import hr.JollyBringer.JollyBringer.service.impl.FeedbackServiceJPA;
import hr.JollyBringer.JollyBringer.service.impl.ParticipantServiceJpa;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityController {


    private final ActivityService activityService;
    private final FeedbackService feedbackService;
    private final ParticipantService participantService;

    public ActivityController(FeedbackService feedbackService, FeedbackServiceJPA feedbackServiceJPA, ActivityService activityService, ActivityServiceJPA activityServiceJPA,
                              ParticipantService participantService, ParticipantServiceJpa participantServiceJpa) {
        this.feedbackService = feedbackService;
        this.activityService = activityService;
        this.participantService = participantService;
    }

    @GetMapping("")
    public List<Activity> getActivities() {
        return activityService.listAll();
    }

    @GetMapping("/{activityId}/feedbacks")
    public ResponseEntity<List<Feedback>> getFeedbackByActivityId(@PathVariable Long activityId) {
        List<Feedback> feedbacks = feedbackService.findByActivityId(activityId);
        return ResponseEntity.ok(feedbacks);
    }

    //TODO beskorisna funkcija?
    @PostMapping("")
    //@Secured("ROLE_ADMIN")
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity){
        Activity saved = activityService.createActivity(activity);
        return ResponseEntity.created(URI.create("/activities/" + saved.getId())).body(saved);
    }



    @PostMapping("/{activityId}/feedback")
    public ResponseEntity<Feedback> addFeedback(
            @PathVariable Long activityId,
            @Valid @RequestBody FeedbackDTO dto
    ) {
        System.out.println(dto);
        Feedback feedback = new Feedback(
                dto.getComment(),
                activityService.fetch(dto.getActivity_id()),
                participantService.fetch(dto.getParticipant_id()),
                dto.getIs_liked()
        );
        System.out.println(feedback);
        feedbackService.createFeedback(feedback);
        return ResponseEntity.created(URI.create("/activities/" + dto.getActivity_id() + "/feedback/" + feedback.getId()))
                .body(feedback);
    }

    @GetMapping("/{id}") //možda   @Secured("ROLE_ADMIN")
    public Activity getActivity(@PathVariable("id") long id) {
        return activityService.fetch(id);
    }

    @PutMapping("/{id}")
    //@Secured("ROLE_ADMIN")
    public ResponseEntity<Activity> updateActivity(@PathVariable("id") long id, @RequestBody Activity activity){
        if (!activity.getId().equals(id))
            throw new IllegalArgumentException("activity ID must be preserved");

       Activity saved = activityService.updateActivity(activity);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    //@Secured("ROLE_ADMIN")
    public Activity deleteActivity(@PathVariable("id") long id){
        feedbackService.deleteRelatedFeedbacks(id);
        return  activityService.deleteActivity(id);
    }



}
