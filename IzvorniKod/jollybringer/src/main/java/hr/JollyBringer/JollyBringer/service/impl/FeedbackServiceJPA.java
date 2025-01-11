package hr.JollyBringer.JollyBringer.service.impl;

import hr.JollyBringer.JollyBringer.dao.FeedbackRepository;
import hr.JollyBringer.JollyBringer.dao.ParticipantGroupRepository;
import hr.JollyBringer.JollyBringer.domain.Activity;
import hr.JollyBringer.JollyBringer.domain.Feedback;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceJPA implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public final ParticipantService participantService;
    public final ActivityService activityService;

    public FeedbackServiceJPA(ParticipantService participantService, ActivityService activityService) {
        this.participantService = participantService;
        this.activityService = activityService;
    }


    @Override
    public List<Feedback> listAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback createFeedback(String comment, String activityName, String username) {
        Optional<Participant> Optionalparticipant = participantService.findByUsername(username);
        Optional<Activity> Optionalactivity = activityService.findByactivityName(activityName);
        if(Optionalparticipant.isPresent() && Optionalactivity.isPresent()){
            Participant participant = Optionalparticipant.get();
            Activity activity = Optionalactivity.get();
            return feedbackRepository.save(new Feedback(comment, activity, participant));
        } else if(Optionalparticipant.isEmpty()){
            throw new RequestDeniedException("No participant with username" + username);
        } else{
            throw new RequestDeniedException("No activity with name" + activityName);
        }



    }

    @Override
    public Feedback fetch(long feedbackId) {
        return findById(feedbackId).orElseThrow(
                () -> new EntityMissingException(Feedback.class, feedbackId)
        );
    }

    @Override
    public Optional<Feedback> findById(long feedbackId) {
        return feedbackRepository.findById(feedbackId);
    }

    @Override
    public Feedback deleteFeedback(long feedbackId) {
        Feedback feedback = fetch(feedbackId);
        feedbackRepository.delete(feedback);
        return feedback;
    }

    @Override
    public Feedback updateFeedback(Feedback feedback) {
        validate(feedback);
        Long feedbackId = feedback.getId();
        if (!feedbackRepository.existsById(feedbackId))
            throw new EntityMissingException(Feedback.class, feedbackId);
        if (feedbackRepository.existsByIdNot( feedbackId ))
            throw new RequestDeniedException(
                    "Feedback with id " + feedbackId + " already exists"
            );
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> findByActivityId(long activityId) {
        return feedbackRepository.findByActivityId(activityId);
    }

    private void validate(Feedback feedback) {
        Assert.notNull(feedback, "activity object must be given");

    }

}
