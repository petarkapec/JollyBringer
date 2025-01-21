package hr.JollyBringer.JollyBringer.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.JollyBringer.JollyBringer.domain.Activity;
import hr.JollyBringer.JollyBringer.domain.ChatMessage;
import hr.JollyBringer.JollyBringer.service.ActivityService;
import hr.JollyBringer.JollyBringer.service.ParticipantGroupService;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mistralai.MistralAiChatModel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController

public class AIChatController {

    private final MistralAiChatModel chatModel;

    private final ActivityService activityService;
    private final ParticipantGroupService participantGroupService;

    @Autowired
    public AIChatController(MistralAiChatModel chatModel, ActivityService activityService, ParticipantGroupService participantGroupService) {
        this.chatModel = chatModel;

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
        if (this.participantGroupService.findById(groupId).isEmpty()) return "Group not found";
        //TODO uredit da vraća iz neke određene grupe chat
        List<ChatMessage>  poruke = this.participantGroupService.findMessageByGroupId(groupId);
        List<ChatMessage> latestMessages = poruke.subList(Math.max(poruke.size() - 20, 0), poruke.size());
        Collections.reverse(latestMessages);
        List<Activity>  aktivnosti = this.activityService.findByGroupId(groupId);
        String contentsString = latestMessages.stream()
                .map(ChatMessage::getContent)
                .collect(Collectors.joining("\n"));
        String descriptionsString = aktivnosti.stream()
                .map(Activity::getActivityName)
                .collect(Collectors.joining("\n"));

        String message = "DON'T GENERATE THE SAME ACTIVITY AS THE PREVOIUS PROMPT, Generate  ONLY THE topic and description (max 100 characters) for ONE christmas activity based on these chat messages,with regex \"Topic:(.*?)--Description:(.*)\", DON't GENERATE ANYTHING ELSE: " + "chat messages: ("+ contentsString + ") \n " + "previous activity names (don't reuse them if you can):( " + descriptionsString + ")";
        System.out.println(message);
        String input = this.chatModel.call(message);
        System.out.println(input);
        Pattern pattern = Pattern.compile("Topic:(.*?)--Description:(.*)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String topic = matcher.group(1).trim();
            String description = matcher.group(2).trim();
            System.out.println(topic);
            System.out.println("----------------");
            System.out.println(description);
            int currentYear = LocalDate.now().getYear();
            int dayOfMonth = new Random().nextInt(25) + 1;
            LocalDate randomDate = LocalDate.of(currentYear, 12, dayOfMonth);
            Activity activity = new Activity(topic, description, randomDate.toString(), "InProgress", participantGroupService.findById(groupId).get(), "AI AGENT", true);
            System.out.println(activity);
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
