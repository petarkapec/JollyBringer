package hr.JollyBringer.JollyBringer.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.JollyBringer.JollyBringer.domain.ChatMessage;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.service.ChatMessageService;
import hr.JollyBringer.JollyBringer.service.ParticipantService;
import hr.JollyBringer.JollyBringer.service.impl.ChatMessageServiceJPA;
import hr.JollyBringer.JollyBringer.service.impl.ParticipantServiceJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;

import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private ParticipantService participantServiceJpa;

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();



    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Definiraj WebSocket endpoint i handler
        registry.addHandler(webSocketHandler(), "/chat")
                .setAllowedOrigins("*");  // Omogućava veze sa svih domena
    }

    public WebSocketHandler webSocketHandler() {
        return new TextWebSocketHandler() {

            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                sessions.add(session);
                System.out.println("New WebSocket connection established: " + session.getId());
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
                sessions.remove(session);
                System.out.println("WebSocket connection closed: " + session.getId());
            }
            @Override
            public void handleTextMessage(org.springframework.web.socket.WebSocketSession session, org.springframework.web.socket.TextMessage message) throws Exception {
                // Ovdje možemo obraditi primljene poruke
                String payload = message.getPayload();
                System.out.println("Received message: " + payload);

                // Pretpostavljamo da je poruka JSON format
                // Parsiraj JSON u objekt (preporučujemo korištenje nekog JSON parsera poput Jacksona)
                ObjectMapper objectMapper = new ObjectMapper();
                ChatMessageDTO incomingMessage = objectMapper.readValue(payload, ChatMessageDTO.class);

                System.out.println("Received message in object format: " + incomingMessage);
                String email =  incomingMessage.getUsername();  // Uzmi ID iz poruke
                Optional<Participant> optionalParticipant = participantServiceJpa.findByEmail(incomingMessage.getUsername());

                // Provjeri ako Participant postoji
                if (!optionalParticipant.isPresent()) {
                    // Ako ne postoji, pošaljite odgovarajuću grešku
                    System.out.println("User not found with username: " + email);
                    ChatMessage responseMessage = new ChatMessage();
                    responseMessage.setContent("User not found with email: " + email);
                    session.sendMessage(new TextMessage(responseMessage.toJson()));
                    return;
                }

                // Ako Participant postoji, nastavi s obradom poruke
                Participant participant = optionalParticipant.get();  // Dohvati Participant iz Optional-a
                String inputTimestamp = incomingMessage.getTimestamp();

                // Parse the input timestamp as an OffsetDateTime
                OffsetDateTime offsetDateTime = OffsetDateTime.parse(inputTimestamp);

                // Adjust to a specific timezone (e.g., UTC-3 or America/Sao_Paulo)
                OffsetDateTime adjustedTime = offsetDateTime.withOffsetSameInstant(ZoneOffset.ofHours(0));

                // Convert to LocalDateTime
                LocalDateTime localDateTime = adjustedTime.toLocalDateTime();

                // Define the desired output format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

                // Format the LocalDateTime to the desired format
                String formattedTimestamp = localDateTime.format(formatter);

                // Spremi poruku u bazu podataka povezanu s Participant-om
                ChatMessage savedMessage = new ChatMessage(participant, incomingMessage.getContent(), formattedTimestamp);



                // Spremi poruku u bazu
                chatMessageService.saveMessage(savedMessage);

                // Emituj poruku svim klijentima
                TextMessage outgoingMessage = new TextMessage(objectMapper.writeValueAsString(incomingMessage));
                for (WebSocketSession clientSession : sessions) {
                    if (clientSession.isOpen()) {
                        clientSession.sendMessage(outgoingMessage);
                    }
                }
            }
        };
    }
}
