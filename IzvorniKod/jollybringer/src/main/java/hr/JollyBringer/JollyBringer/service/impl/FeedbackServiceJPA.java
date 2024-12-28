package hr.JollyBringer.JollyBringer.service.impl;

import hr.JollyBringer.JollyBringer.dao.FeedbackRepository;
import hr.JollyBringer.JollyBringer.domain.Activity;
import hr.JollyBringer.JollyBringer.domain.Feedback;
import hr.JollyBringer.JollyBringer.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class FeedbackServiceJPA implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public List<Feedback> listAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback createFeedback(Feedback feedback) {
        validate(feedback);
        Assert.isNull(feedback.getId(),
                "Activity ID must be null, not: " + feedback.getId()
        );

        return feedbackRepository.save(feedback);

    }

    private void validate(Feedback feedback) {
        Assert.notNull(feedback, "activity object must be given");

    }
}
