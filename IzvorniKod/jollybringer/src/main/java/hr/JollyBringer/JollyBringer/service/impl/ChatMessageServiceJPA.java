package hr.JollyBringer.JollyBringer.service.impl;

import hr.JollyBringer.JollyBringer.domain.ChatMessage;
import hr.JollyBringer.JollyBringer.rest.ChatMessageDTO;
import hr.JollyBringer.JollyBringer.domain.Participant;
import hr.JollyBringer.JollyBringer.dao.ChatMessageRepository;

import hr.JollyBringer.JollyBringer.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatMessageServiceJPA implements ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;


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
    public List<ChatMessage> listAll() {
        return chatMessageRepository.findAll();
    }
}