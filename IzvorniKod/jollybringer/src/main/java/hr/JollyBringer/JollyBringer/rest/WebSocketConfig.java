package hr.JollyBringer.JollyBringer.rest;

import hr.JollyBringer.JollyBringer.domain.ChatMessage;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.sql.Timestamp;
import java.util.Date;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

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
                System.out.println("Received message: " + message.getPayload());

                // Možemo poslati poruku natrag

                ChatMessage PovratnaPoruka = new ChatMessage();
                PovratnaPoruka.setContent("Poruka: " + message.getPayload());
                PovratnaPoruka.setSender("Baza");
                Timestamp timestamp = new Timestamp(new Date().getTime());
                PovratnaPoruka.setTimestamp(timestamp.toString());
                session.sendMessage(new TextMessage(PovratnaPoruka.toJson()));
            }
        };
    }
}
