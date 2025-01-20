package hr.JollyBringer.JollyBringer.rest;

import hr.JollyBringer.JollyBringer.domain.Activity;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.domain.ParticipantGroup;
import hr.JollyBringer.JollyBringer.service.ActivityService;
import hr.JollyBringer.JollyBringer.service.ParticipantGroupService;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import hr.JollyBringer.JollyBringer.service.RequestDeniedException;
import hr.JollyBringer.JollyBringer.service.impl.ActivityServiceJPA;
import hr.JollyBringer.JollyBringer.service.impl.ParticipantGroupServiceJPA;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Set;

//TODO add controls for activities
@RestController
@RequestMapping("/groups")
//@CrossOrigin(origins = "http://localhost:5173")
public class ParticipantGroupController {


    private final ParticipantGroupService participantGroupService;
    private final ActivityService activityService;



    public ParticipantGroupController(ParticipantService participantService, ParticipantGroupService participantGroupService, ParticipantGroupServiceJPA participantGroupServiceJPA, ActivityService activityService, ActivityServiceJPA activityServiceJPA) {

        this.participantGroupService = participantGroupService;
        this.activityService = activityService;
    }

    @GetMapping("")
    //@Secured("ROLE_LEAD")
    public List<ParticipantGroup> listGroups() {
        return participantGroupService.listAll();
    }



    @GetMapping("/{id}")//možda samo admin?
    public ParticipantGroup getGroup(@PathVariable("id") Long id) {
        return participantGroupService.fetch(id);
    }

    @GetMapping("/{groupId}/activities")
    public ResponseEntity<List<Activity>> getActivitiesByGroupId(@PathVariable Long groupId) {
        List<Activity> activities = activityService.findByGroupId(groupId);
        return ResponseEntity.ok(activities);
    }

    @PostMapping("/{groupId}/activities")
    public ResponseEntity<List<Activity>> createActivitiesByGroupId(@RequestBody ActivityDTO dto) {
        System.out.println(dto);
        Activity activity = new Activity(dto.getActivity_name(), dto.getDescription(), dto.getDate(), dto.getActivity_status(), participantGroupService.fetch(dto.getGroup_id()), dto.getCreated_by());
        System.out.println("creating activity");
        activityService.createActivity(activity);
        return ResponseEntity.created(URI.create("/groups/" + dto.getGroup_id() + "/activities/" +  activity.getId())).body(Collections.singletonList(activity));
    }

    @PostMapping("")
    //@Secured("ROLE_LEAD")
    public ResponseEntity<ParticipantGroup> createGroup(@RequestBody CreateGroupDTO dto, @AuthenticationPrincipal OAuth2User u) {
        System.out.println("creating group");
        ParticipantGroup saved = participantGroupService.createGroup(dto.getName(), u.getAttribute("email"));
        if (dto.getUsers() != null) participantGroupService.addMembers(saved.getId(), dto.getUsers() );
        return ResponseEntity.created(URI.create("/groups/" + saved.getId())).body(saved);
    }

    @DeleteMapping("/{groupId}")
    public void deleteGroup(@PathVariable("groupId") Long groupId) {
         participantGroupService.deleteGroup(groupId);
    }

    @GetMapping("/{gid}/members") //možda samo admin?
    public Set<Participant> getGroupMembers(@PathVariable("gid") Long id) {
        return participantGroupService.fetch(id).getMembers();
    }

    //change name of group
    @PatchMapping("/{gid}")
    //@Secured("ROLE_LEAD")
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
    //@Secured("ROLE_LEAD")
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
    //@Secured("ROLE_LEAD")
    public ResponseEntity<?> removeGroupMember(
            @PathVariable("gid") Long gid,
            @PathVariable("pid") Long pid,
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

