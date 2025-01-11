package hr.JollyBringer.JollyBringer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.JollyBringer.JollyBringer.domain.Activity;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.domain.ParticipantGroup;
import hr.JollyBringer.JollyBringer.domain.Role;
import hr.JollyBringer.JollyBringer.rest.ActivityDTO;
import hr.JollyBringer.JollyBringer.rest.GroupDTO;
import hr.JollyBringer.JollyBringer.rest.RoleDTO;
import hr.JollyBringer.JollyBringer.rest.UserDTO;
import hr.JollyBringer.JollyBringer.service.ActivityService;
import hr.JollyBringer.JollyBringer.service.ParticipantGroupService;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import hr.JollyBringer.JollyBringer.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DataInitializer {

  @Autowired
  private ParticipantService participantService;

  @Autowired
  private ParticipantGroupService participantGroupService;

  @Autowired
  private RoleService roleService; // Repository for fetching roles

  @Autowired
  private ActivityService activityService;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @EventListener
  public void appReady(ApplicationReadyEvent event) {
    try {
      InputStream inputStreamR = getClass().getResourceAsStream("/static/roles.json");
      Assert.notNull(inputStreamR, "roles.json file not found in resources");
      // Parse JSON to list of Role DTOs
      List<RoleDTO> roles = objectMapper.readValue(inputStreamR, new TypeReference<List<RoleDTO>>() {
      });
      // Save each user to the database
      for (RoleDTO roleDTO : roles) {

        Role role = new Role(roleDTO.getRole_id(), roleDTO.getRole_name());


        // Save user
        roleService.createRole(role);
      }

      System.out.println("Roles successfully initialized from JSON file");

      // Load JSON file
      InputStream inputStream = getClass().getResourceAsStream("/static/example_users.json");
      Assert.notNull(inputStream, "users.json file not found in resources");

      // Parse JSON to list of User DTOs
      List<UserDTO> users = objectMapper.readValue(inputStream, new TypeReference<List<UserDTO>>() {
      });

      // Save each user to the database
      for (UserDTO userDto : users) {

        Participant user = new Participant();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());

        // Find role by ID and set it on user
        Role role = roleService.findById(userDto.getRole_id())
                .orElseThrow(() -> new IllegalArgumentException("Role not found for id: " + userDto.getRole_id()));
        user.setRole(role);

        // Save user
        participantService.createParticipant(user);
      }

      System.out.println("Users successfully initialized from JSON file");
      ///

      // Load JSON file
      InputStream GroupStream = getClass().getResourceAsStream("/static/groups.json");
      Assert.notNull(GroupStream, "groups.json file not found in resources");

      // Parse JSON to list of User DTOs
      List<GroupDTO> groups = objectMapper.readValue(GroupStream, new TypeReference<List<GroupDTO>>() {
      });

      // Save each user to the database
      for (GroupDTO groupDto : groups) {



        // Find role by ID and set it on user
        Participant president = participantService.findByEmail(groupDto.getPresidentEmail())
                .orElseThrow(() -> new IllegalArgumentException("president not found for mail: " + groupDto.getPresidentEmail()));


        // Save user
        participantGroupService.createGroup(groupDto.getGroupname(), president.getEmail());
        Optional<ParticipantGroup> myGroup = participantGroupService.fetchByName(groupDto.getGroupname());
        if (myGroup.isEmpty()) throw new IllegalArgumentException("Group not found for name: " + groupDto.getGroupname());
        List<Long> members = new ArrayList<>();

        for (String memberName : groupDto.getUsers()){
              Optional<Participant> member = participantService.findByEmail(memberName);
              if(member.isEmpty()) throw new IllegalArgumentException("Member not found for name: " + memberName);
              members.add(member.get().getId());

        }
        participantGroupService.addMembers(myGroup.get().getId(), members);

      }

      System.out.println("Groups successfully initialized from JSON file");

      //-----------------------------------
      // Load JSON file
      InputStream ActivityStream = getClass().getResourceAsStream("/static/activities.json");
      Assert.notNull(ActivityStream, "activities.json file not found in resources");

      // Parse JSON to list of User DTOs
      List<ActivityDTO> activities= objectMapper.readValue(ActivityStream, new TypeReference<List<ActivityDTO>>() {
      });

      // Save each user to the database
      for (ActivityDTO activityDto : activities) {
        if(participantGroupService.findById(activityDto.getGROUP_ID()).isEmpty()) throw new IllegalArgumentException("Group not found for id: " + activityDto.getGROUP_ID());
        Activity activity = new Activity(activityDto.getACTIVITY_NAME(), activityDto.getDESCRIPTION(), activityDto.getDATE(), activityDto.getACTIVITY_STATUS(),activityDto.getCREATED_BY(), participantGroupService.findById(activityDto.getGROUP_ID()).get());

        activityService.createActivity(activity);



      }

      System.out.println("Activities successfully initialized from JSON file");
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Failed to initialize users from JSON file");
    }
  }


}