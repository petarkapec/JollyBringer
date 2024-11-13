package hr.JollyBringer.JollyBringer.service.impl;

import hr.JollyBringer.JollyBringer.dao.ParticipantGroupRepository;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.domain.ParticipantGroup;
import hr.JollyBringer.JollyBringer.service.EntityMissingException;
import hr.JollyBringer.JollyBringer.service.ParticipantGroupService;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import hr.JollyBringer.JollyBringer.service.RequestDeniedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.Set;

//TODO: implement methods

@Service
public class ParticipantGroupServiceJPA implements ParticipantGroupService
{

    //TODO placeholder, maybe no limit or other limit
    @Value("${opp.group.max-size}")
    private int groupMaxSize;

    public final ParticipantService participantService;
    public final ParticipantGroupRepository participantGroupRepo;

    public ParticipantGroupServiceJPA(ParticipantGroupRepository participantGroupRepo, ParticipantService participantService) {
        this.participantService = participantService;
        this.participantGroupRepo = participantGroupRepo;
    }


    @Override
    public List<ParticipantGroup> listAll() {
        return participantGroupRepo.findAll();
    }

    @Override
    public Optional<ParticipantGroup> findById(long participantGroupId) {
        return participantGroupRepo.findById(participantGroupId);
    }

    @Override
    public ParticipantGroup fetch(long participantGroupId) {
        return findById(participantGroupId).orElseThrow(
                () -> new EntityMissingException(ParticipantGroup.class, participantGroupId));
    }

    @Override
    public ParticipantGroup createGroup(String groupName, String presidentUsername) {
        Participant president = participantService.findByUsername(presidentUsername).orElseThrow(
                // NOTE: not throwing EntityMissingException because that is just for missing resources from URI
                () -> new RequestDeniedException("No student with username " + presidentUsername)
        );
        Assert.isTrue(president.isPresident(),
                "Group president must be a lead, not: " + president);
        /*participantGroupRepo.findByMember(president).ifPresent(g -> {
            throw new RequestDeniedException(president + " already member of " + g);
        });*/
        participantGroupRepo.findByName(groupName).ifPresent(g -> {
            throw new RequestDeniedException(groupName + " already name of " + g); }
        );
        return participantGroupRepo.save(new ParticipantGroup(groupName, president));
    }

    @Override
    public ParticipantGroup updateGroupName(long groupId, String name) {
        Assert.hasText(name, "Group name must be provided");
        ParticipantGroup group = fetch(groupId);
        if (participantGroupRepo.existsByNameAndIdNot(name, groupId)) {
            throw new RequestDeniedException("Another group already has name '" + name + "'");
        }
        group.setName(name);
        return participantGroupRepo.save(group);
    }

    @Override
    public Set<Participant> listMembers(long groupId) {
        return fetch(groupId).getMembers();
    }

    @Override
    public boolean addMember(long groupId, long participantId) {
        ParticipantGroup group = fetch(groupId);
        Participant participant = participantService.fetch(participantId);
        Assert.isTrue(!participant.isPresident(), "Must be non-lead to be a member: " + participant);
        participantGroupRepo.findByMember(participant).filter(g -> !g.getId().equals(groupId)).ifPresent(g -> {
            throw new RequestDeniedException(participant + " already member of " + g); }
        );
        if (group.getMembers().size() >= groupMaxSize)
            throw new RequestDeniedException("Already at max size (" + groupMaxSize + "): " + group);
        boolean added = group.getMembers().add(participant);
        if (added)
            participantGroupRepo.save(group);
        return added;
    }

    @Override
    public boolean removeMember(long groupId, long participantId) {
        ParticipantGroup group = fetch(groupId);
        if (group.getPresident().getId().equals(participantId))
            throw new RequestDeniedException("Cannot remove president from group " + group);
        Set<Participant> members = group.getMembers();
        boolean removed = members.remove(participantService.fetch(participantId));
        if (removed)
            participantGroupRepo.save(group);
        return removed;
    }

    public Optional<ParticipantGroup> findByMember(long participantId) {
        return participantGroupRepo.findByMember(participantService.fetch(participantId));

    }
}
