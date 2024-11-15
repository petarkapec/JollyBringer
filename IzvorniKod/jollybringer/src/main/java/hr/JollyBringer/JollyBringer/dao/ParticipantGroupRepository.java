package hr.JollyBringer.JollyBringer.dao;

import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.domain.ParticipantGroup;
import jakarta.servlet.http.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


//TODO: implement methods
public interface ParticipantGroupRepository extends JpaRepository<ParticipantGroup, Long> {

    @Query("SELECT g FROM ParticipantGroup g WHERE :s MEMBER OF g.members")
    Optional<ParticipantGroup> findByMember(@Param("s") Participant president);

    Optional<ParticipantGroup> findByName(String groupName);

    boolean existsByNameAndIdNot(String name, long groupId);
}
