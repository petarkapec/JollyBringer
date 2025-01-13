package hr.JollyBringer.JollyBringer.rest;

import java.time.LocalDateTime;

public class ChatMessageDTO {

    private String username; // Promjena iz participantId u username
    private String content;
    private String timestamp;

    public ChatMessageDTO() {}

    // Konstruktor
    public ChatMessageDTO( String username, String content, String timestamp) {

        this.username = username;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getters i Setters


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}