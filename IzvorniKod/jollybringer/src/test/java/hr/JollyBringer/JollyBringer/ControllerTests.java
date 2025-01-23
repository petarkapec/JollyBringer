package hr.JollyBringer.JollyBringer;

import hr.JollyBringer.JollyBringer.dao.ParticipantRepository;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.domain.Role;
import hr.JollyBringer.JollyBringer.rest.ApplicationController;
import hr.JollyBringer.JollyBringer.rest.ApplicationDTO;
import hr.JollyBringer.JollyBringer.rest.ParticipantController;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;


@SpringBootTest
public class ControllerTests {

    Participant tester;


    private final ParticipantService participantService;

    private final ApplicationController applicationController;



    @Autowired
    public ControllerTests(ParticipantService participantService, ApplicationController applicationController) {
        this.participantService = participantService;
        this.applicationController = applicationController;

    }

    @BeforeAll
    public static void setUp() {
       System.out.println("Starting controller tests");

    }

    @BeforeEach
    public void setUp2() {
        tester = new Participant("Danko123", "danko@samplemail.com", new Role(1L, "PARTICIPANT"));
        participantService.createParticipant(tester);
        if (participantService.findByEmail("danko@samplemail.com").isEmpty())
            throw new RuntimeException();
        tester = participantService.findByEmail("danko@samplemail.com").get();

    }
    @Test
    public void testApplyForPresident() {

        ApplicationDTO applicationDTO = new ApplicationDTO(tester.getId(), true);

        applicationController.applyForPresident(applicationDTO);
        applicationController.approveApplication(applicationDTO);
        if (participantService.findByEmail("danko@samplemail.com").isEmpty())
            throw new RuntimeException();
        tester = participantService.findByEmail("danko@samplemail.com").get();
        Assert.isTrue(tester.getRole().getName().equals("President"), "User is not president");
    }
    @AfterEach
    public void tearDown() {
        participantService.deleteParticipant(tester.getId());
    }



}
