package hr.JollyBringer.JollyBringer.service.impl;

import hr.JollyBringer.JollyBringer.dao.ActivityRepository;
import hr.JollyBringer.JollyBringer.domain.Activity;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.service.ActivityService;
import hr.JollyBringer.JollyBringer.service.RequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
@Service
public class ActivityServiceJPA implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public List<Activity> listAll() {
        return activityRepository.findAll();
    }

    @Override
    public Activity createActivity(Activity activity) {

            validate(activity);
            Assert.isNull(activity.getId(),
                    "Activity ID must be null, not: " + activity.getId()
            );

            return activityRepository.save(activity);

    }

    private void validate(Activity activity) {
        Assert.notNull(activity, "activity object must be given");

    }


}
