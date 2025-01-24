package hr.JollyBringer.JollyBringer.dao;

import hr.JollyBringer.JollyBringer.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

//TODO: implement methods
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findByUsername(String username);

    int countByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, Long participantId);

    Optional<Participant> findByEmail(String email);

    int countByEmail(String username);

    @Query("SELECT p FROM Participant p WHERE p.role_id.name = :string")
    List<Participant> findAllWithRole(@Param("string") String string);
}
