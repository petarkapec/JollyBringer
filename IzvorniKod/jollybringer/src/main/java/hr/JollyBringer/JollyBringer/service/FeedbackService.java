package hr.JollyBringer.JollyBringer.service;

import hr.JollyBringer.JollyBringer.domain.Activity;
import hr.JollyBringer.JollyBringer.domain.Feedback;

import java.util.List;

public interface FeedbackService {
    /**
     * Lists all Activities in the system.
     * @return a list with all Participants
     */
    List<Feedback> listAll();
    Feedback createFeedback(Feedback feedback);
}
