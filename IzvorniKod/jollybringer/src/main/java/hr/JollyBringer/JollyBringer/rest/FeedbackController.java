package hr.JollyBringer.JollyBringer.rest;

import hr.JollyBringer.JollyBringer.domain.Feedback;
import hr.JollyBringer.JollyBringer.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;
    @GetMapping("")
    public List<Feedback> getActivities() {
        return feedbackService.listAll();
    }

    @GetMapping("/{id}") //možda   @Secured("ROLE_ADMIN")
    public Feedback getFeedback(@PathVariable("id") long id) {
        return feedbackService.fetch(id);
    }

    @PostMapping("")
    //@Secured("ROLE_ADMIN")
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback){
        Feedback saved = feedbackService.createFeedback(feedback);
        return ResponseEntity.created(URI.create("/feedbacks/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    //@Secured("ROLE_ADMIN")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable("id") long id, @RequestBody Feedback feedback){
        if (!feedback.getId().equals(id))
            throw new IllegalArgumentException("feedback ID must be preserved");

        Feedback saved = feedbackService.updateFeedback(feedback);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    //@Secured("ROLE_ADMIN")
    public Feedback deleteFeedback(@PathVariable("id") long id){

        return  feedbackService.deleteFeedback(id);
    }
}
