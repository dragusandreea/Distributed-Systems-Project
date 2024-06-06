package sd.project.chatservice.services;

import sd.project.chatservice.entities.ChatMessage;

import java.util.List;
import java.util.UUID;

public interface ChatMessageService {
    ChatMessage add(ChatMessage chatMessage);
    ChatMessage update(ChatMessage chatMessage);
    ChatMessage getById(UUID id);
    List<ChatMessage> getAllBySenderUidAndReceiverUid(UUID senderUid, UUID receiverUid);
}
