package hr.JollyBringer.JollyBringer.service.impl;

import hr.JollyBringer.JollyBringer.dao.ActivityRepository;
import hr.JollyBringer.JollyBringer.domain.Activity;
import hr.JollyBringer.JollyBringer.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ActivityServiceJPA implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public List<Activity> listAll() {
        return activityRepository.findAll();
    }


}
