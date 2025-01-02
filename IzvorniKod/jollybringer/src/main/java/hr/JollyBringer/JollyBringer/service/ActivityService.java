package hr.JollyBringer.JollyBringer.service;

import hr.JollyBringer.JollyBringer.domain.Activity;
import hr.JollyBringer.JollyBringer.domain.Participant;

import java.util.List;
import java.util.Optional;

public interface ActivityService {
    /**
     * Lists all Activities in the system.
     * @return a list with all Participants
     */
    List<Activity> listAll();

    Activity fetch(long activitytId);
    Activity createActivity(Activity activity);

    Optional<Activity> findById(long activityId);
    Optional<Activity> findByUsername(String username);

    Activity updateActivity(Activity activity);
    Activity deleteActivity(long activityId);
}
