package hr.JollyBringer.JollyBringer.service.impl;

import hr.JollyBringer.JollyBringer.dao.ActivityRepository;
import hr.JollyBringer.JollyBringer.domain.Activity;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.service.ActivityService;
import hr.JollyBringer.JollyBringer.service.EntityMissingException;
import hr.JollyBringer.JollyBringer.service.RequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceJPA implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public List<Activity> listAll() {
        return activityRepository.findAll();
    }

    @Override
    public Activity fetch(long activityId) {
        return findById(activityId).orElseThrow(
                () -> new EntityMissingException(Activity.class, activityId)
        );
    }

    @Override
    public Activity createActivity(Activity activity) {

            validate(activity);
            Assert.isNull(activity.getId(),
                    "Activity ID must be null, not: " + activity.getId()
            );

            return activityRepository.save(activity);

    }

    @Override
    public Optional<Activity> findById(long activityId) {
        return activityRepository.findById(activityId);
    }

    public Optional<Activity> findByactivityName(String activityName) {
        Assert.notNull(activityName, "activityName must be given");
        return activityRepository.findByActivityName(activityName);
    }

    @Override
    public Activity updateActivity(Activity activity) {
        validate(activity);
        Long activityId = activity.getId();
        if (!activityRepository.existsById(activityId))
            throw new EntityMissingException(Activity.class, activityId);
        if (activityRepository.existsByActivityNameAndIdNot(activity.getActivityName(), activityId))
            throw new RequestDeniedException(
                    "Activity with username" + activity.getActivityName() + " already exists"
            );
        return activityRepository.save(activity);
    }

    @Override
    public Activity deleteActivity(long activityId) {
        Activity activity = fetch(activityId);
       activityRepository.delete(activity);
        return activity;
    }

    private void validate(Activity activity) {
        Assert.notNull(activity, "activity object must be given");

    }




}
