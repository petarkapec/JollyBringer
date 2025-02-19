package hr.JollyBringer.JollyBringer.service;

import hr.JollyBringer.JollyBringer.domain.Activity;
import java.util.List;
import java.util.Optional;

public interface ActivityService {
    /**
     * Lists all Activities in the system.
     * @return a list with all Activities
     */
    List<Activity> listAll();

    List<Activity> findByGroupId(long groupId);

    List<Activity> findByCreatedBy(String username);

    Activity fetch(long activitytId);
    Activity createActivity(Activity activity);

    Optional<Activity> findById(long activityId);
    Optional<Activity> findByactivityName(String activityName);

    Activity updateActivity(Activity activity);
    Activity deleteActivity(long activityId);

    List<Activity> findByGroupIdRegular(Long groupId);

    List<Activity> findByGroupIdCalendar(Long groupId);
}
