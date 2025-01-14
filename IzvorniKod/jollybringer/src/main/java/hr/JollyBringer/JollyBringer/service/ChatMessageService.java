package hr.JollyBringer.JollyBringer.service;

import hr.JollyBringer.JollyBringer.domain.ChatMessage;
import hr.JollyBringer.JollyBringer.rest.ChatMessageDTO;

import java.util.List;

public interface ChatMessageService {
    public List<ChatMessageDTO> getLast7Messages();
    public void saveMessage(ChatMessage savedMessage);
    public List<ChatMessage> listAll();
}
