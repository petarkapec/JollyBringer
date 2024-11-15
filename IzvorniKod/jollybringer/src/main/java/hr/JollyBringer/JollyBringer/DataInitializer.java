package hr.JollyBringer.JollyBringer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.domain.Role;
import hr.JollyBringer.JollyBringer.rest.RoleDTO;
import hr.JollyBringer.JollyBringer.rest.UserDTO;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import hr.JollyBringer.JollyBringer.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.List;

@Component
public class DataInitializer {

  @Autowired
  private ParticipantService participantService;

  @Autowired
  private RoleService roleService; // Repository for fetching roles

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

    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Failed to initialize users from JSON file");
    }
  }


}