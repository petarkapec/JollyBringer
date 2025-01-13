package hr.JollyBringer.JollyBringer.dao;

import hr.JollyBringer.JollyBringer.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByParticipantId(Long participantId);

    List<ChatMessage> findTop7ByTimestampAfterOrderByTimestampDesc(String timestamp);
}