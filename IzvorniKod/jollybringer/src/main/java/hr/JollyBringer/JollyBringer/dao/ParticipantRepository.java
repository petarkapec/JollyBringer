package hr.JollyBringer.JollyBringer.dao;

import hr.JollyBringer.JollyBringer.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//TODO: implement methods
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findByUsername(String username);

    int countByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, Long participantId);

    Optional<Participant> findByEmail(String email);

    int countByEmail(String username);
}
