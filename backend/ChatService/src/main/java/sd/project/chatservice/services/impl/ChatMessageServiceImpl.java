package sd.project.chatservice.services.impl;

import org.springframework.stereotype.Service;
import sd.project.chatservice.entities.ChatMessage;
import sd.project.chatservice.exceptions.CouldNotSendMessageException;
import sd.project.chatservice.exceptions.UserNotFoundException;
import sd.project.chatservice.repositories.ChatMessageRepository;
import sd.project.chatservice.services.ChatMessageService;
import sd.project.chatservice.services.UserService;

import java.util.List;
import java.util.UUID;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;
    

    public ChatMessageServiceImpl(ChatMessageRepository chatMessageRepository, UserService userService) {
        this.chatMessageRepository = chatMessageRepository;
        this.userService = userService;
    }

    @Override
    public ChatMessage add(ChatMessage chatMessage) {
        try {
            userService.getById(chatMessage.getSenderUid());
            userService.getById(chatMessage.getReceiverUid());
        } catch (UserNotFoundException userNotFoundException) {
            throw new CouldNotSendMessageException("Sender or receiver not found");
        }
        return chatMessageRepository.save(chatMessage);
    }

    @Override
    public ChatMessage update(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    @Override
    public ChatMessage getById(UUID id) {
        return chatMessageRepository.findById(id).get();
    }

    @Override
    public List<ChatMessage> getAllBySenderUidAndReceiverUid(UUID senderUid, UUID receiverUid) {
        return chatMessageRepository.findAllBySenderUidAndReceiverUid(senderUid, receiverUid);
    }
}
