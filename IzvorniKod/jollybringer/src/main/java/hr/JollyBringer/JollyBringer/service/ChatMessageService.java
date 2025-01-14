package hr.JollyBringer.JollyBringer.service;

import hr.JollyBringer.JollyBringer.domain.Activity;
import hr.JollyBringer.JollyBringer.domain.ChatMessage;
import hr.JollyBringer.JollyBringer.rest.ChatMessageDTO;

import java.util.List;
import java.util.Optional;

public interface ChatMessageService {
    public List<ChatMessageDTO> getLast7Messages();
    public void saveMessage(ChatMessage savedMessage);

    public List<ChatMessage> findByParticipantId(Long participantId);

    ChatMessage fetch(long chatMessageId);

    Optional<ChatMessage> findById(long chatMessageId);

    void deleteMessage(Long id);
    List<ChatMessage> listAll();
}
