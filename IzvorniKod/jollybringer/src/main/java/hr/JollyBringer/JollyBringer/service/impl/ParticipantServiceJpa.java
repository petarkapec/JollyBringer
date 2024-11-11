package hr.JollyBringer.JollyBringer.service.impl;

import hr.JollyBringer.JollyBringer.dao.ParticipantRepository;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.domain.RoleId;
import hr.JollyBringer.JollyBringer.service.EntityMissingException;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import hr.JollyBringer.JollyBringer.service.RequestDeniedException;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

//TODO: implement methods
//TODO validate username?

@Service
public class ParticipantServiceJpa implements ParticipantService {

    public final ParticipantRepository participantRepo;

    public ParticipantServiceJpa(ParticipantRepository participantRepo) {
        this.participantRepo = participantRepo;
    }

    @Override
    public List<Participant> listAll() {
        return participantRepo.findAll();
    }

    @Override
    public Participant fetch(long participantId) {
        return findById(participantId).orElseThrow(
                () -> new EntityMissingException(Participant.class, participantId)
        );
    }

    @Override
    public Participant createParticipant(Participant participant) {
        validate(participant);
        Assert.isNull(participant.getId(),
                "Participant ID must be null, not: " + participant.getId()
        );

        if (participantRepo.countByUsername(participant.getUsername()) > 0)
            throw new RequestDeniedException(
                    "Participantwith Username" + participant.getUsername() + " already exists"
            );
        return participantRepo.save(participant);


    }

    @Override
    public Optional<Participant> findById(long participantId) {
        return participantRepo.findById(participantId);
    }

    @Override
    public Optional<Participant> findByUsername(String username) {
        Assert.notNull(username, "Username must be given");
        return participantRepo.findByUsername(username);
    }

    @Override
    public Participant updateParticipant(Participant participant) {
        validate(participant);
        Long participantId = participant.getId();
        if (!participantRepo.existsById(participantId))
            throw new EntityMissingException(Participant.class, participantId);
        if (participantRepo.existsByUsernameAndIdNot(participant.getUsername(), participantId))
            throw new RequestDeniedException(
                    "Participant with username" + participant.getUsername() + " already exists"
            );
        return participantRepo.save(participant);
    }

    @Override
    public Participant deleteParticipant(long participantId) {
        Participant participant = fetch(participantId);
        participantRepo.delete(participant);
        return participant;
    }

    @Override
    public Participant findByEmail(String email) {
        Assert.notNull(email, "Username must be given");
        return participantRepo.findByEmail(email);
    }

    //TODO change if needed
    private void validate(Participant participant) {
        Assert.notNull(participant, "participant object must be given");
        Assert.isTrue( EnumUtils.isValidEnum(RoleId.class, participant.getRole()), "Role must be set");
    }
}