package hr.JollyBringer.JollyBringer.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.JollyBringer.JollyBringer.domain.ChatMessage;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.service.impl.ChatMessageServiceJPA;
import hr.JollyBringer.JollyBringer.service.impl.ParticipantServiceJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private ChatMessageServiceJPA chatMessageService;
    @Autowired
    private ParticipantServiceJpa participantServiceJpa;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Definiraj WebSocket endpoint i handler
        registry.addHandler(webSocketHandler(), "/chat")
                .setAllowedOrigins("*");  // Omogućava veze sa svih domena
    }

    public WebSocketHandler webSocketHandler() {
        return new TextWebSocketHandler() {
            @Override
            public void handleTextMessage(org.springframework.web.socket.WebSocketSession session, org.springframework.web.socket.TextMessage message) throws Exception {
                // Ovdje možemo obraditi primljene poruke
                String payload = message.getPayload();
                System.out.println("Received message: " + payload);

                // Pretpostavljamo da je poruka JSON format
                // Parsiraj JSON u objekt (preporučujemo korištenje nekog JSON parsera poput Jacksona)
                ObjectMapper objectMapper = new ObjectMapper();
                ChatMessage incomingMessage = objectMapper.readValue(payload, ChatMessage.class);

                Long userId = incomingMessage.getId();  // Uzmi ID iz poruke
                Optional<Participant> optionalParticipant = participantServiceJpa.findById(userId);

                // Provjeri ako Participant postoji
                if (!optionalParticipant.isPresent()) {
                    // Ako ne postoji, pošaljite odgovarajuću grešku
                    System.out.println("User not found with ID: " + userId);
                    ChatMessage responseMessage = new ChatMessage();
                    responseMessage.setContent("User not found with ID: " + userId);
                    session.sendMessage(new TextMessage(responseMessage.toJson()));
                    return;
                }

                // Ako Participant postoji, nastavi s obradom poruke
                Participant participant = optionalParticipant.get();  // Dohvati Participant iz Optional-a

                // Spremi poruku u bazu podataka povezanu s Participant-om
                ChatMessage savedMessage = new ChatMessage();
                savedMessage.setParticipant(participant);  // Poveži s Participant-om iz baze
                savedMessage.setContent(incomingMessage.getContent());
                savedMessage.setTimestamp(LocalDateTime.now().toString());

                // Spremi poruku u bazu
                chatMessageService.saveMessage(savedMessage);
            }
        };
    }
}
