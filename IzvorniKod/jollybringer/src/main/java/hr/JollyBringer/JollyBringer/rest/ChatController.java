package hr.JollyBringer.JollyBringer.rest;

import hr.JollyBringer.JollyBringer.domain.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import hr.JollyBringer.JollyBringer.service.impl.ChatMessageServiceJPA;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/poruke")
public class ChatController {

    @Autowired
    private ChatMessageServiceJPA chatMessageService;

    // Dohvati zadnjih 7 poruka
    @GetMapping("/last7")
    public List<ChatMessageDTO> getLast7Messages() {
        return chatMessageService.getLast7Messages();
    }

    @MessageMapping("/sendMessage") // Maps /app/sendMessage (from client) to this controller method
    @SendTo("/topic/messages") // Sends the response to /topic/messages
    public ChatMessage sendMessage(ChatMessage message) {
        return message; // Just broadcasting the message for now
    }
}
