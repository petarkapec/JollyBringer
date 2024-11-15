package hr.JollyBringer.JollyBringer.dao;

import hr.JollyBringer.JollyBringer.domain.ApplicationRequest;
import hr.JollyBringer.JollyBringer.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<ApplicationRequest, Long> {

    int countByUser(Participant user);

    boolean existsByUser(Participant userId);

    @Query("SELECT a FROM ApplicationRequest a WHERE a.user.id = :userId")
    Optional<ApplicationRequest> findByUserId(@Param("userId") long userId);


}
