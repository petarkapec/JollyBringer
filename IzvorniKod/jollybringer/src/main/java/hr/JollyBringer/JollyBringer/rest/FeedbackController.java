package hr.JollyBringer.JollyBringer.rest;

import hr.JollyBringer.JollyBringer.domain.Feedback;
import hr.JollyBringer.JollyBringer.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;
    @GetMapping("")
    public List<Feedback> getActivities() {
        return feedbackService.listAll();
    }

    @PostMapping("")
    //@Secured("ROLE_ADMIN")
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback){
        Feedback saved = feedbackService.createFeedback(feedback);
        return ResponseEntity.created(URI.create("/feedback/" + saved.getId())).body(saved);
    }
}
