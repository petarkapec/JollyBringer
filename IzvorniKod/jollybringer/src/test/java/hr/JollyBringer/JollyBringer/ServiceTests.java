package hr.JollyBringer.JollyBringer;

import hr.JollyBringer.JollyBringer.domain.*;
import hr.JollyBringer.JollyBringer.rest.*;
import hr.JollyBringer.JollyBringer.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class ServiceTests {

    Participant tester;
    ParticipantGroup testGroup;


    private final ParticipantService participantService;
    private final ParticipantGroupService participantGroupService;
    private final ActivityService activityService;
    private final FeedbackService feedbackService;

    private final ApplicationController applicationController;
    private final ActivityController activityController;
    private final ParticipantGroupController participantGroupController;



    @Autowired
    public ServiceTests(ParticipantService participantService, ParticipantGroupService participantGroupService, ActivityService activityService, FeedbackService feedbackService, ApplicationController applicationController, ActivityController activityController, ParticipantGroupController participantGroupController) {
        this.participantService = participantService;
        this.participantGroupService = participantGroupService;
        this.activityService = activityService;
        this.feedbackService = feedbackService;
        this.applicationController = applicationController;
        this.activityController = activityController;
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

    @Test //izazivanje iznimke
    public void testSameGroupName(){ //
        Participant newTester = new Participant("Kreso", "kreso@samplemail.com", new Role(2L, "PRESIDENT"));
        participantService.createParticipant(newTester);
        Exception exception = assertThrows(RequestDeniedException.class, () -> {
            participantGroupService.createGroup("SampleGroup", newTester.getEmail());
        });
        participantService.deleteParticipant(newTester.getId());

        // Provjera poruke u iznimci kako bismo potvrdili ispravnost
        String expectedMessage = "SampleGroup already name of";
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);

        assertTrue(actualMessage.contains(expectedMessage), "Exception message does not match!");
    }

    @Test //neimplementirana dunkcija
    public void testNonimplementedMethod(){
        PointsService service = new PointsService();
        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            service.addPoints();
        });

        String expectedMessage = "Ova metoda jos nije implementirana.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage), "Exception message does not match!");
    }

    @Test //rubni slucaj, brisanje feedbacka bez aktivnosti
    public void testDeleteFeedbackforNoActivity(){

        Activity activity = new Activity("SampleActivity", "SampleDescription", "2021-01-01", "InProgress", testGroup, tester.getEmail(), true);
        activityService.createActivity(activity);

        Feedback feedback = new Feedback("SuperFeedback", activityService.findByactivityName("SampleActivity").get(), tester, "Like");

        feedbackService.createFeedback(feedback);
        List<Feedback> lista = feedbackService.findByActivityId(activityService.findByactivityName("SampleActivity").get().getId());
        Feedback gotten = new Feedback();
        for (Feedback f : lista) {
            gotten = f;
        }
        activityController.deleteActivity(activity.getId());
        assertTrue(feedbackService.findById(gotten.getId()).isEmpty(), "Feedback is not deleted");
    }


    @AfterEach
    public void tearDown() {
        participantGroupService.deleteGroup(testGroup.getId());
        participantService.deleteParticipant(tester.getId());
    }



}
