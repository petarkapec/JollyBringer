package hr.JollyBringer.JollyBringer.service;

import hr.JollyBringer.JollyBringer.domain.ChatMessage;
import hr.JollyBringer.JollyBringer.rest.ChatMessageDTO;

import java.util.List;
import java.util.Optional;

public interface ChatMessageService {
    public List<ChatMessageDTO> getLast20Messages();
    public void saveMessage(ChatMessage savedMessage);
    public List<ChatMessage> findByParticipantId(Long participantId);

    public ChatMessage fetch(long chatMessageId);

    public Optional<ChatMessage> findById(long chatMessageId);

    public void deleteMessage(Long id);
    public List<ChatMessage> listAll();
}
