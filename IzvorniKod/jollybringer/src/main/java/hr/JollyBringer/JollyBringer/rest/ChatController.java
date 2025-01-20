package hr.JollyBringer.JollyBringer.rest;

import hr.JollyBringer.JollyBringer.domain.ChatMessage;
import hr.JollyBringer.JollyBringer.service.ChatMessageService;
import hr.JollyBringer.JollyBringer.service.ParticipantGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/poruke")
public class ChatController {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ParticipantGroupService participantGroupService;

    @GetMapping("")
    public List<ChatMessage> getMessages() {
        return chatMessageService.listAll();
    }
    // Dohvati zadnjih 7 poruka
    @GetMapping("/last7")
    public List<ChatMessageDTO> getLast20Messages() {
        return chatMessageService.getLast20Messages();
    }


    @GetMapping("/{groupId}")
    public List<ChatMessageDTO> getMessagesByGroupId(@PathVariable("groupId") Long groupId) {
        ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
        List<ChatMessage> poruke = participantGroupService.findMessageByGroupId(groupId);
        return poruke.stream().map(chatMessage -> new ChatMessageDTO(

                chatMessage.getParticipant().getUsername(), // Dohvati username
                chatMessage.getContent(),
                chatMessage.getTimestamp()
        )).collect(Collectors.toList());

    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable("id") long id) {
        chatMessageService.deleteMessage(id);
    }

    @MessageMapping("/sendMessage") // Maps /app/sendMessage (from client) to this controller method
    @SendTo("/topic/messages") // Sends the response to /topic/messages
    public ChatMessage sendMessage(ChatMessage message) {
        return message; // Just broadcasting the message for now
    }
}
