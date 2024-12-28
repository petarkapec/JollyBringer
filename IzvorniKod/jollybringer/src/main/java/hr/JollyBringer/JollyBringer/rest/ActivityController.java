package hr.JollyBringer.JollyBringer.rest;

import hr.JollyBringer.JollyBringer.domain.Activity;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;
    @GetMapping("")
    public List<Activity> getActivities() {
        return activityService.listAll();
    }

    @PostMapping("")
    //@Secured("ROLE_ADMIN")
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity){
        Activity saved = activityService.createActivity(activity);
        return ResponseEntity.created(URI.create("/activities/" + saved.getId())).body(saved);
    }
}
