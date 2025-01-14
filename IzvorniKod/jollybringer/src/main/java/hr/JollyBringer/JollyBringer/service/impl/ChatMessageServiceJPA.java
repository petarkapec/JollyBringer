package hr.JollyBringer.JollyBringer.service.impl;

import hr.JollyBringer.JollyBringer.domain.Activity;
import hr.JollyBringer.JollyBringer.domain.ChatMessage;
import hr.JollyBringer.JollyBringer.rest.ChatMessageDTO;
import hr.JollyBringer.JollyBringer.dao.ChatMessageRepository;

import hr.JollyBringer.JollyBringer.service.ChatMessageService;
import hr.JollyBringer.JollyBringer.service.EntityMissingException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatMessageServiceJPA implements ChatMessageService {


    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageServiceJPA(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public List<ChatMessage> listAll() {
        return chatMessageRepository.findAll();
    }


    // Metoda za dohvaćanje zadnjih 7 poruka na temelju timestamp-a
    public List<ChatMessageDTO> getLast7Messages() {
        String sevenDaysAgo = LocalDateTime.now().minusDays(7).toString();
        List<ChatMessage> chatMessages = chatMessageRepository.findTop7ByTimestampAfterOrderByTimestampDesc(sevenDaysAgo);
        System.out.println("Dohvacene poruke: " + chatMessages);
        // Prebacivanje u DTO s username-om umjesto ID-a
        return chatMessages.stream().map(chatMessage -> new ChatMessageDTO(

                chatMessage.getParticipant().getUsername(), // Dohvati username
                chatMessage.getContent(),
                chatMessage.getTimestamp()
        )).collect(Collectors.toList());
    }

    public void saveMessage(ChatMessage savedMessage) {
        chatMessageRepository.save(savedMessage);
    }

    @Override
    public ChatMessage fetch(long chatMessageId) {
        return findById(chatMessageId).orElseThrow(
                () -> new EntityMissingException(ChatMessage.class, chatMessageId)
        );
    }

    public Optional<ChatMessage> findById(long chatMessageId) {
        return chatMessageRepository.findById(chatMessageId);
    }

    @Override
    public List<ChatMessage> findByParticipantId(Long participantId) {
        return chatMessageRepository.findByParticipantId(participantId);
    }

    @Override
    public void deleteMessage(Long id) {
        ChatMessage message = fetch(id);
        chatMessageRepository.delete(message);
    }
}