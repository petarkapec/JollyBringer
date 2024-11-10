package hr.JollyBringer.JollyBringer.service;

import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.service.EntityMissingException;
import hr.JollyBringer.JollyBringer.service.RequestDeniedException;
import hr.JollyBringer.JollyBringer.service.ParticipantService;

import java.util.List;
import java.util.Optional;

public interface ParticipantService {
    /**
     * Lists all Participants in the system.
     * @return a list with all Participants
     */
    List<Participant> listAll();

    /**
     * Fetches Participant with given ID.
     * @param participantId given Participant ID
     * @return Participant associated with given ID in the system
     * @throws EntityMissingException if Participant with that ID is not found
     * @see ParticipantService#findById(long)
     */
    Participant fetch(long participantId);
    // Note: verb "fetch" in method name is typically used when identified object is expected

    /**
     * Creates new Participant in the system.
     * @param participant object to create, with ID set to null
     * @return created Participant object in the system with ID set
     * @throws IllegalArgumentException if given Participant is null, or its ID is NOT null,
     * or its JMBAG is null or invalid
     * @throws RequestDeniedException if Participant with that JMBAG already exists in the system
     * @see Participant
     */
    Participant createParticipant(Participant participant);

    /**
     * Finds participant with given ID, if exists.
     * @param participantId given Participant ID
     * @return Optional with value of Participant associated with given ID in the system,
     * or no value if one does not exist
     * @see ParticipantService#fetch
     */
    Optional<Participant> findById(long participantId);

    /**
     * Finds the Participant with given username.
     * @param username Participant username
     * @return Optional with value of a Participant associated with given username exists in the system,
     * no value otherwise
     * @throws IllegalArgumentException if given username is null
     */
    Optional<Participant> findByUsername(String username);

    /**
     * Updates the Participant with that same ID.
     * @param participant object to update, with ID set
     * @return updated Participant object in the system
     * @throws IllegalArgumentException if given object is null, has null ID, or has null or invalid username
     * @throws EntityMissingException if Participant with given ID is not found
     * @throws RequestDeniedException if another Participant with some other ID and the same username already exists
     * @see ParticipantService#createParticipant(participant)
     */
    Participant updateParticipant(Participant participant);

    /**
     * Deletes one Participant.
     * @param participantId ID of Participant to delete from the system
     * @return deleted data
     * @throws EntityMissingException if Participant with that ID is not found
     */
    Participant deleteParticipant(long participantId);
}
