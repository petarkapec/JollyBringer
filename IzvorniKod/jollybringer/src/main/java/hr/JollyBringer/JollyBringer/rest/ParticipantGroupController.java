package hr.JollyBringer.JollyBringer.rest;

import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.domain.ParticipantGroup;
import hr.JollyBringer.JollyBringer.service.ParticipantGroupService;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import hr.JollyBringer.JollyBringer.service.RequestDeniedException;
import hr.JollyBringer.JollyBringer.service.impl.ParticipantGroupServiceJPA;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Set;

//TODO add controls for activities
@RestController
@RequestMapping("/groups")
public class ParticipantGroupController {

    private final ParticipantService participantService;
    private final ParticipantGroupService participantGroupService;



    public ParticipantGroupController(ParticipantService participantService, ParticipantGroupService participantGroupService, ParticipantGroupServiceJPA participantGroupServiceJPA) {
        this.participantService = participantService;
        this.participantGroupService = participantGroupService;
    }

    @GetMapping("")
    public List<ParticipantGroup> listGroups() {
        return participantGroupService.listAll();
    }

    @GetMapping("/{id}")//možda samo admin?
    public ParticipantGroup getGroup(@PathVariable("id") Long id) {
        return participantGroupService.fetch(id);
    }

    @PostMapping("")
    @Secured("ROLE_LEAD")
    public ResponseEntity<ParticipantGroup> createGroup(@RequestBody CreateGroupDTO dto, @AuthenticationPrincipal User u) {
        ParticipantGroup saved = participantGroupService.createGroup(dto.getName(), u.getUsername());
        return ResponseEntity.created(URI.create("/groups/" + saved.getId())).body(saved);
    }

    @GetMapping("/{gid}/members") //možda samo admin?
    public Set<Participant> getGroupMembers(@PathVariable("gid") Long id) {
        return participantGroupService.fetch(id).getMembers();
    }

    //change name of group
    @PatchMapping("/{gid}")
    @Secured("ROLE_LEAD")
    public ParticipantGroup patchName(
            @PathVariable("gid") Long groupId,
            @RequestBody CreateGroupDTO dto,
            @AuthenticationPrincipal User u)
    {
        String presidentUsername = participantGroupService.fetch(groupId).getPresident().getUsername();
        if (!presidentUsername.equals(u.getUsername()))
            throw new RequestDeniedException("Only group president can change group name, not: " + u);
        return participantGroupService.updateGroupName(groupId, dto.getName());
    }

    //only the president can add members, if he's the president of the searched group
    @PutMapping("/{gid}/members/{pid}")
    @Secured("ROLE_LEAD")
    public ResponseEntity<?> addGroupMember(
            @PathVariable("gid") Long gid,
            @PathVariable("pid") Long pid,
            @AuthenticationPrincipal User u)
    {
        checkAllowedToChangeMembers(gid, u.getUsername());
        return ResponseEntity.ok(participantGroupService.addMember(gid, pid));
    }

    //only the president can remove members, if he's the president of the searched group
    @DeleteMapping("/{gid}/members/{pid}")
    @Secured("ROLE_LEAD")
    public ResponseEntity<?> removeGroupMember(
            @PathVariable("gid") Long gid,
            @PathVariable("gid") Long pid,
            @AuthenticationPrincipal User u)
    {
        checkAllowedToChangeMembers(gid, u.getUsername());
        return ResponseEntity.ok(participantGroupService.removeMember(gid, pid));
    }

    private void checkAllowedToChangeMembers(Long gid, String username) {
        String presidentUsername = participantGroupService.fetch(gid).getPresident().getUsername();
        if (!presidentUsername.equals(username))
            throw new RequestDeniedException(
                    "Only the Christmas president (" + presidentUsername + ") can add to group #" + gid
                            + ", not: " + username
            );

    }

}

