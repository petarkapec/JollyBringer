package hr.JollyBringer.JollyBringer.service.impl;

import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.domain.ParticipantGroup;
import hr.JollyBringer.JollyBringer.service.ParticipantGroupService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

//TODO: implement methods

@Service
public class ParticipantGroupServiceJPA implements ParticipantGroupService
{
    @Override
    public List<ParticipantGroup> listAll() {
        return List.of();
    }

    @Override
    public Optional<ParticipantGroup> findById(long participantGroupId) {
        return Optional.empty();
    }

    @Override
    public ParticipantGroup fetch(long participantGroupId) {
        return null;
    }

    @Override
    public ParticipantGroup createParticipantGroup(String participantGroupName, String coordinatorJmbag) {
        return null;
    }

    @Override
    public ParticipantGroup updateParticipantGroupName(long participantGroupId, String name) {
        return null;
    }

    @Override
    public Set<Participant> listMembers(long participantGroupId) {
        return Set.of();
    }

    @Override
    public boolean addMember(long participantGroupId, long participantId) {
        return false;
    }

    @Override
    public boolean removeMember(long participantGroupId, long participantId) {
        return false;
    }
}
