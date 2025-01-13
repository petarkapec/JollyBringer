package hr.JollyBringer.JollyBringer.dao;

import hr.JollyBringer.JollyBringer.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    boolean existsByIdNot(Long participantId);

    List<Feedback> findByActivityId(Long activityId);

}
