package hr.JollyBringer.JollyBringer.rest;

import hr.JollyBringer.JollyBringer.domain.Activity;
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

        return  activityService.deleteActivity(id);
    }



}
