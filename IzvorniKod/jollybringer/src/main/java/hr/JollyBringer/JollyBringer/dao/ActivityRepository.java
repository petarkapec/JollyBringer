package hr.JollyBringer.JollyBringer.dao;

import hr.JollyBringer.JollyBringer.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<Activity> findByActivityName(String activityName);
    boolean existsByActivityNameAndIdNot(String activityName, Long activityId);

    List<Activity> findByGroupId(Long groupId);



}
