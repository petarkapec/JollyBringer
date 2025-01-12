package hr.JollyBringer.JollyBringer.rest;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Message broker for subscription prefixes
        config.enableSimpleBroker("/topic"); // Use destinations like /topic/chatroom
        config.setApplicationDestinationPrefixes("/app"); // Prefix for messages from client to server
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket endpoint for client connections
        registry.addEndpoint("/chat").setAllowedOrigins("*").withSockJS(); // Fallback for browsers not supporting WebSockets
    }
}
