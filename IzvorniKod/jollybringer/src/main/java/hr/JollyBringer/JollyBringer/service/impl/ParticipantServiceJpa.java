package hr.JollyBringer.JollyBringer.service.impl;

import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//TODO: implement methods

@Service
public class ParticipantServiceJpa implements ParticipantService {
    @Override
    public List<Participant> listAll() {
        return List.of();
    }

    @Override
    public Participant fetch(long participantId) {
        return null;
    }

    @Override
    public Participant createParticipant(Participant participant) {
        return null;
    }

    @Override
    public Optional<Participant> findById(long participantId) {
        return Optional.empty();
    }

    @Override
    public Optional<Participant> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Participant updateParticipant(Participant participant) {
        return null;
    }

    @Override
    public Participant deleteParticipant(long participantId) {
        return null;
    }
}
