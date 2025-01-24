package hr.JollyBringer.JollyBringer.repository;


import hr.JollyBringer.JollyBringer.dao.ParticipantRepository;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.domain.Role;
import hr.JollyBringer.JollyBringer.domain.RoleId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ParticipantRepositoryTest {

    @Autowired
    private ParticipantRepository participantRepository;

    private Participant testParticipant;

    @BeforeEach
    void setUp() {
        testParticipant = new Participant();
        testParticipant.setUsername("testuser");
        testParticipant.setEmail("teste@mail.com");
        testParticipant.setRole(new Role(1L,"PARTICIPANT"));
        // Set other fields if necessary
        participantRepository.save(testParticipant);
    }

    @Test
    void testFindByUsername() {
        Optional<Participant> foundParticipant = participantRepository.findByUsername("testuser");
        assertThat(foundParticipant).isPresent();
        assertThat(foundParticipant.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    void testCountByUsername() {
        int count = participantRepository.countByUsername("testuser");
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testExistsByUsernameAndIdNot() {
        boolean exists = participantRepository.existsByUsernameAndIdNot("testuser", 999L);
        assertThat(exists).isTrue();
    }
}

