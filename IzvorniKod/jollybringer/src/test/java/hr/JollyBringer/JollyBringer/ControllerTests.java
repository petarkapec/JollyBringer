package hr.JollyBringer.JollyBringer;

import hr.JollyBringer.JollyBringer.dao.ParticipantRepository;
import hr.JollyBringer.JollyBringer.domain.Activity;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.domain.ParticipantGroup;
import hr.JollyBringer.JollyBringer.domain.Role;
import hr.JollyBringer.JollyBringer.rest.*;
import hr.JollyBringer.JollyBringer.service.ActivityService;
import hr.JollyBringer.JollyBringer.service.ParticipantGroupService;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;


@SpringBootTest
public class ControllerTests {

    Participant tester;
    ParticipantGroup testGroup;


    private final ParticipantService participantService;
    private final ParticipantGroupService participantGroupService;
    private final ActivityService activityService;

    private final ApplicationController applicationController;

    private final ParticipantGroupController participantGroupController;



    @Autowired
    public ControllerTests(ParticipantService participantService, ParticipantGroupService participantGroupService, ActivityService activityService, ApplicationController applicationController, ParticipantGroupController participantGroupController) {
        this.participantService = participantService;
        this.participantGroupService = participantGroupService;
        this.activityService = activityService;
        this.applicationController = applicationController;
        this.participantGroupController = participantGroupController;
    }

    @BeforeAll
    public static void setUp() {
       System.out.println("Starting controller tests");

    }

    @BeforeEach
    public void setUp2() {
        tester = new Participant("Danko123", "danko@samplemail.com", new Role(2L, "PRESIDENT"));
        participantService.createParticipant(tester);
        if (participantService.findByEmail("danko@samplemail.com").isEmpty())
            throw new RuntimeException();
        tester = participantService.findByEmail("danko@samplemail.com").get();
        participantGroupService.createGroup("SampleGroup", tester.getEmail());
        testGroup = participantGroupService.fetchByName("SampleGroup").get();


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
    @Test
    public void testCreateActivity(){
        ActivityDTO activityDTO = new ActivityDTO("SampleActivity", "SampleDescription", "2021-01-01", "InProgress", testGroup.getId(), tester.getUsername());

        participantGroupController.createActivitiesByGroupIdCalendar(activityDTO);
        Activity activity = activityService.findByactivityName("SampleActivity").get();
        Assert.isTrue(activity.getActivityName().equals("SampleActivity"), "Activity not created");


    }
    @AfterEach
    public void tearDown() {
        participantGroupService.deleteGroup(testGroup.getId());
        participantService.deleteParticipant(tester.getId());
    }



}
