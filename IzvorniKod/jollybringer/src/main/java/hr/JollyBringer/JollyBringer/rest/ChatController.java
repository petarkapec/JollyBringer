package hr.JollyBringer.JollyBringer.rest;

import hr.JollyBringer.JollyBringer.domain.ChatMessage;
import hr.JollyBringer.JollyBringer.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import hr.JollyBringer.JollyBringer.service.impl.ChatMessageServiceJPA;

import java.util.List;

@RestController
@RequestMapping("/poruke")
public class ChatController {

    @Autowired
    private ChatMessageService chatMessageService;

    @GetMapping("")
    public List<ChatMessage> getMessages() {
        return chatMessageService.listAll();
    }
    // Dohvati zadnjih 7 poruka
    @GetMapping("/last7")
    public List<ChatMessageDTO> getLast7Messages() {
        return chatMessageService.getLast7Messages();
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
