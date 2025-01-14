package hr.JollyBringer.JollyBringer.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.JollyBringer.JollyBringer.domain.Activity;
import hr.JollyBringer.JollyBringer.domain.ChatMessage;
import hr.JollyBringer.JollyBringer.service.ActivityService;
import hr.JollyBringer.JollyBringer.service.ChatMessageService;
import hr.JollyBringer.JollyBringer.service.ParticipantGroupService;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mistralai.MistralAiChatModel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
public class AIChatController {

    private final MistralAiChatModel chatModel;
    private final ChatMessageService chatMessageService;
    private final ActivityService activityService;
    private final ParticipantGroupService participantGroupService;

    @Autowired
    public AIChatController(MistralAiChatModel chatModel, ChatMessageService chatMessageService, ActivityService activityService, ParticipantGroupService participantGroupService) {
        this.chatModel = chatModel;
        this.chatMessageService = chatMessageService;
        this.activityService = activityService;
        this.participantGroupService = participantGroupService;
    }

    @GetMapping("/ai/generate")
    public Map<String,String> generate(@RequestParam(value = "message", defaultValue = "Tell me how to finish my project on time") String message) {
        return Map.of("generation", this.chatModel.call(message));
    }

    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me how to finish my project on time") String message) {
        var prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt);
    }

    //http://localhost:8080/ai/create-activity/${selectedGroup.id}

    @GetMapping("/ai/create-activity/{groupId}")
    public String generateActivity(@PathVariable Long groupId) throws JsonProcessingException {
        if (participantGroupService.findById(groupId).isEmpty()) return "Group not found";
        //TODO uredit da vraća iz neke određene grupe chat
        List<ChatMessage>  poruke = this.chatMessageService.listAll();
        List<Activity>  aktivnosti = this.activityService.findByGroupId(groupId);
        String contentsString = poruke.stream()
                .map(ChatMessage::getContent)
                .collect(Collectors.joining("\n"));
        String descriptionsString = aktivnosti.stream()
                .map(Activity::getDescription)
                .collect(Collectors.joining("\n"));
        String message = "Generate  ONLY THE topic and description for ONE christmas activity based on these strings,with regex \"Topic:(.*?)--Description:(.*)\", DON't GENERATE ANYTHING ELSE: " + contentsString + " \n " + descriptionsString;

        String input = this.chatModel.call(message);
        System.out.println(input);
        Pattern pattern = Pattern.compile("Topic:(.*?)--Description:(.*)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String topic = matcher.group(1).trim();
            String description = matcher.group(2).trim();

            LocalDate today = LocalDate.now();
            LocalDate nextMonth = today.plusMonths(1);

            int dayOfMonth = new Random().nextInt(nextMonth.lengthOfMonth()) + 1;
            LocalDate randomDate = nextMonth.withDayOfMonth(dayOfMonth);
            Activity activity = new Activity(topic, description, randomDate.toString(), "InProgress", participantGroupService.findById(groupId).get(), "AI AGENT");
            activityService.createActivity(activity);
            ActivityDTO activityDTO = new ActivityDTO(
                    activity.getActivityName(),
                    activity.getDescription(),
                    activity.getDate(),
                    activity.getActivity_status().toString(),
                    activity.getGroup().getId(),
                    activity.getCreatedBy()

            );
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(activityDTO);
            return json;
        }
        else {
            return "not found";
        }
    }
}
