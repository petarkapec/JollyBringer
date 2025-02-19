package hr.JollyBringer.JollyBringer.service;

import hr.JollyBringer.JollyBringer.domain.Activity;
import hr.JollyBringer.JollyBringer.domain.Feedback;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {
    /**
     * Lists all Activities in the system.
     * @return a list with all Participants
     */
    List<Feedback> listAll();
    Feedback createFeedback(String comment, String activityName, String username, String isLiked);
    Feedback createFeedback(Feedback feedback);
    Feedback fetch(long feedbackId);
    Optional<Feedback> findById(long feedbackId);
    Feedback deleteFeedback(long feedbackId);
    Feedback updateFeedback(Feedback feedback);
    void deleteRelatedFeedbacks(long activityId);

    List<Feedback> findByActivityId(long activityId);


}
