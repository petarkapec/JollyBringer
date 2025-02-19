package hr.JollyBringer.JollyBringer.service.impl;

import hr.JollyBringer.JollyBringer.dao.ParticipantGroupRepository;
import hr.JollyBringer.JollyBringer.domain.Activity;
import hr.JollyBringer.JollyBringer.domain.ChatMessage;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.domain.ParticipantGroup;
import hr.JollyBringer.JollyBringer.domain.Role;
import hr.JollyBringer.JollyBringer.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

//TODO: implement methods

@Service
@Transactional
public class ParticipantGroupServiceJPA implements ParticipantGroupService
{


    //TODO placeholder, maybe no limit or other limit
    @Value("${opp.group.max-size}")
    private int groupMaxSize;

    public final ParticipantService participantService;
    public final ParticipantGroupRepository participantGroupRepo;
    public final FeedbackService feedbackService;
    public final ActivityService activityService;

    public ParticipantGroupServiceJPA(ParticipantGroupRepository participantGroupRepo, ParticipantService participantService,
                                      FeedbackService feedbackService, ActivityService activityService) {
        this.participantService = participantService;
        this.participantGroupRepo = participantGroupRepo;
        this.feedbackService = feedbackService;
        this.activityService = activityService;
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
        Participant president = participantService.findByEmail(presidentUsername).orElseThrow(
                // NOTE: not throwing EntityMissingException because that is just for missing resources from URI
                () -> new RequestDeniedException("No student with email " + presidentUsername)
        );
        Assert.isTrue(president.isPresident() || president.isAdmin(),
                "Group president " + presidentUsername + " must be a lead or admin, not: " + president.getRole().getName());
        participantGroupRepo.findByMember(president).ifPresent(g -> {
            throw new RequestDeniedException(president + " already member of " + g);
        }); //TODO enable multiple groups
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
        System.out.println("Adding member " + participantId + " to group " + groupId);
        ParticipantGroup group = fetch(groupId);
        Participant participant = participantService.fetch(participantId);
        Assert.isTrue(!participant.isPresident(), "Must be non-lead to be a member: " + participant);
        participantGroupRepo.findByMember(participant).filter(g -> !g.getId().equals(groupId)).ifPresent(g -> {
            throw new RequestDeniedException(participant + " already member of " + g); }
        );
        //if (group.getMembers().size() >= groupMaxSize)
            //throw new RequestDeniedException("Already at max size (" + groupMaxSize + "): " + group);
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

    @Override
    public boolean removeMember(long groupId) {
        ParticipantGroup group = fetch(groupId);
        Set<Participant> members = group.getMembers();
        boolean removed = false;

        if (members != null && !members.isEmpty()) {
            members.clear();
            removed = true;
        }

        if (removed) {
            participantGroupRepo.save(group);
        }
        return removed;
    }

    @Override
    public Optional<ParticipantGroup> findByMember(Participant president) {
        return participantGroupRepo.findByMember(president);
    }

    @Override
    public List<ParticipantGroup> findByPresident(Participant president) {
        return participantGroupRepo.findByPresident(president);
    }

    @Override
    public boolean addMessageToGroup(ChatMessage savedMessage) {
        Participant covjek = savedMessage.getParticipant();
        if (covjek == null)
            throw new EntityMissingException(Participant.class, savedMessage.getParticipant().getId());
        if (findByMember(covjek).isEmpty())
            throw new RequestDeniedException(covjek + " not member of any group");
        ParticipantGroup grupa = findByMember(covjek).get();

        participantGroupRepo.save(grupa);
        boolean added = grupa.getMessages().add(savedMessage);;
        if (added)
            participantGroupRepo.save(grupa);
        return added;
    }

    @Override
    public void addMembers(Long id, List<Long> users) {
        for (Long userId : users) {
            addMember(id, userId);
        }
    }

    @Override
    public Optional<ParticipantGroup> fetchByName(String name) {
        return participantGroupRepo.findByName(name);
    }

    @Override
    public void deleteGroup(Long id) {

        List<Activity> activities = activityService.findByGroupId(id);
        if(activities.size() > 0) {
            for (Activity activity : activities) {
                feedbackService.deleteRelatedFeedbacks(activity.getId());
                activityService.deleteActivity(activity.getId());
            }
        }
        removeMember(id);
        participantGroupRepo.deleteById(id);
    }

    public Optional<ParticipantGroup> findByMember(long participantId) {
        return participantGroupRepo.findByMember(participantService.fetch(participantId));

    }

    @Override
    public List<ChatMessage> findMessageByGroupId(Long groupId) {
        if(findById(groupId) == null) {
            throw new EntityMissingException(ParticipantGroup.class, groupId);
        }
        else {
            List<ChatMessage> list = new ArrayList<>(findById(groupId).get().getMessages());
            return list;

        }
    }


}
