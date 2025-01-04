package hr.JollyBringer.JollyBringer.service.impl;

import hr.JollyBringer.JollyBringer.dao.FeedbackRepository;
import hr.JollyBringer.JollyBringer.domain.Activity;
import hr.JollyBringer.JollyBringer.domain.Feedback;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.service.EntityMissingException;
import hr.JollyBringer.JollyBringer.service.FeedbackService;
import hr.JollyBringer.JollyBringer.service.RequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

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

    private void validate(Feedback feedback) {
        Assert.notNull(feedback, "activity object must be given");

    }

}
