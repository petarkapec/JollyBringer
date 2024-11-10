package hr.JollyBringer.JollyBringer.service;

import hr.JollyBringer.JollyBringer.domain.ParticipantGroup;
import hr.JollyBringer.JollyBringer.domain.Participant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Manages participant groups in the system.
 * @see ParticipantGroup
 */
@Service
public interface ParticipantGroupService {

    /**
     * Lists all participant groups.
     * @return iterable containing all participant groups
     */
    List<ParticipantGroup> listAll();

    /**
     * Finds participant group with given ID, if exists.
     * @param participantGroupId given participant group ID
     * @return Optional with value of participant group associated with given ID in the system,
     * or no value if one does not exist
     * @see participanGroupService#fetch
     */
    Optional<ParticipantGroup> findById(long participantGroupId);

    /**
     * Fetches participant group with given ID.
     * @param participantGroupId given participant group ID
     * @return participant group associated with given ID
     * @throws EntityMissingException if participant group with that ID not found in the system
     */
    ParticipantGroup fetch(long participantGroupId);

    /**
     * Creates new participant group with given coordinator and name.
     * Coordinator cannot be changed later, and is always member of this participant group.
     * @param participantGroupName name of the new participant group
     * @param coordinatorJmbag JMBAG of participant assigned as coordinator of the new participant group
     * @return created participant group object, with ID set and member list consisting only of participant group coordinator
     * @throws IllegalArgumentException if name is empty or any is <code>null</code>
     * @throws RequestDeniedException if no participant with given JMBAG,
     * or participant with that JMBAG is already a member of another participant group
     */
    ParticipantGroup createGroup(String participantGroupName, String coordinatorJmbag);

    /**
     * Updates the name of a given participant group.
     * @param participantGroupId identifies participant group to update
     * @param name new name of the participant group
     * @return updated participant group object
     * @throws EntityMissingException if entity with the same ID as in parameter does not exist
     * @throws IllegalArgumentException if name is empty or any is <code>null</code>
     */
    ParticipantGroup updateGroupName(long participantGroupId, String name);

    /**
     * Lists all members of participant group, including the coordinator.
     * @param participantGroupId ID of participant group to list
     * @return set contains all members of the participant group
     * @throws EntityMissingException if participant group not found
     * @throws IllegalArgumentException if argument <code>null</code>
     */
    Set<Participant> listMembers(long participantGroupId);

    /**
     * Adds a member to given participant group. Member cannot be added if in another participant group.
     * @param participantGroupId identifies participant group to which given member is to be added
     * @param participantId identifies participant to add
     * @return <code>true</code> if member was newly added to participant group,
     * <code>false</code> if it was already a member.
     * @throws EntityMissingException if an entity with one or the other ID is not found
     * @throws RequestDeniedException if already member of another participant group, or participant group too large
     * @throws IllegalArgumentException if any argument <code>null</code>,
     * or participant is lead
     */
    boolean addMember(long participantGroupId, long participantId);

    /**
     * Removes given member from given participant group. participant group's coordinator cannot be removed.
     * @param participantGroupId identifies participant group from which to remove given member
     * @param participantId identifies participant to remove
     * @return <code>true</code> if member was removed from participant group,
     * <code>false</code> if the participant wasn't a member to begin with.
     * @throws EntityMissingException if an entity with one or the other ID is not found
     * @throws RequestDeniedException if coordinator of given participant group
     * @throws IllegalArgumentException if any argument <code>null</code>
     */
    boolean removeMember(long participantGroupId, long participantId);
}

