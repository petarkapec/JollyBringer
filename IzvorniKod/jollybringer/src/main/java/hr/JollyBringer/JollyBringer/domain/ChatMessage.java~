package hr.JollyBringer.JollyBringer.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "chatmessage")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participant; // Links to the Participant entity

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String timestamp;

    // Constructors
    public ChatMessage() {}

    public ChatMessage(Participant participant, String content, String timestamp) {
        this.participant = participant;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this); // Convert object to JSON string
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}"; // Return empty JSON string in case of error
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
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
