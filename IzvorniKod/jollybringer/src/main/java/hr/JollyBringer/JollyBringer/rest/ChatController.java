package hr.JollyBringer.JollyBringer.rest;
import hr.JollyBringer.JollyBringer.domain.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/sendMessage") // Maps /app/sendMessage (from client) to this controller method
    @SendTo("/topic/messages") // Sends the response to /topic/messages
    public ChatMessage sendMessage(ChatMessage message) {
        return message; // Just broadcasting the message for now
    }
}
