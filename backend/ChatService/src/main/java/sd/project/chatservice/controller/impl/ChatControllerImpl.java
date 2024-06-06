package sd.project.chatservice.controller.impl;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sd.project.chatservice.controller.ChatController;
import sd.project.chatservice.dtos.ChatMessageDto;
import sd.project.chatservice.entities.ChatMessage;
import sd.project.chatservice.security.JwtService;
import sd.project.chatservice.services.ChatMessageService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ChatControllerImpl implements ChatController {
    private final ChatMessageService chatMessageService;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    public ChatControllerImpl(ChatMessageService chatMessageService, JwtService jwtService,  ModelMapper modelMapper) {
        this.chatMessageService = chatMessageService;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void processMessage(ChatMessageDto chatMessageDto) {
        if(chatMessageDto.getId() == null) {
            ChatMessage chatMessage = modelMapper.map(chatMessageDto, ChatMessage.class);
            chatMessage.setSentTimestamp(Timestamp.valueOf(LocalDateTime.now()));
            chatMessageService.add(chatMessage);
        }
    }

    @Override
    public void processMessage2(String uid) {

    }

    @Override
    public ResponseEntity<List<ChatMessageDto>> getAllBySenderIdAndReceiverId(String token, UUID senderId, UUID receiverId) {
        List<ChatMessageDto> list = null;
        HttpStatus status = jwtService.authorizeByRole(token, senderId, receiverId);
        if(status.equals(HttpStatus.OK)) {
            list = chatMessageService.getAllBySenderUidAndReceiverUid(senderId, receiverId)
                    .stream()
                    .map(user -> modelMapper.map(user, ChatMessageDto.class))
                    .collect(Collectors.toList());
        }
        return new ResponseEntity<>(list, status);
    }

    @Override
    public ResponseEntity<List<ChatMessageDto>> getAllBetweenSenderIdAndReceiverId(String token, UUID senderId, UUID receiverId) {
        List<ChatMessage> chatMessages = chatMessageService.getAllBySenderUidAndReceiverUid(senderId, receiverId);
        List<ChatMessage> chatMessages2 = chatMessageService.getAllBySenderUidAndReceiverUid(receiverId, senderId);
        chatMessages.addAll(chatMessages2);
        chatMessages.sort(Comparator.comparing(ChatMessage::getSentTimestamp));
        List<ChatMessageDto> list = null;
        HttpStatus status = jwtService.authorizeByRole(token, senderId, receiverId);
        if(status.equals(HttpStatus.OK)) {
            list = chatMessages
                    .stream()
                    .map(user -> modelMapper.map(user, ChatMessageDto.class))
                    .collect(Collectors.toList());
        }
        return new ResponseEntity<>(list, status);
    }

    @Override
    public ResponseEntity<ChatMessageDto> updateSeenChatMessage(String token, ChatMessageDto chatMessageDto) {
        ChatMessageDto chatMessageDto1 = null;
        HttpStatus status = jwtService.authorizeByRole(token, chatMessageDto.getSenderUid(), chatMessageDto.getReceiverUid());
        if(status.equals(HttpStatus.OK)) {
            ChatMessage chatMessage = modelMapper.map(chatMessageDto, ChatMessage.class);
            ChatMessage foundMessage = chatMessageService.getById(chatMessage.getId());
            foundMessage.setSeen(true);
            foundMessage.setSeenTimestamp(Timestamp.valueOf(LocalDateTime.now()));
            foundMessage.setSentTimestamp(foundMessage.getSentTimestamp());
            ChatMessage updatedChatMessage = chatMessageService.update(foundMessage);
            chatMessageDto1 = modelMapper.map(updatedChatMessage, ChatMessageDto.class);
        }
        return new ResponseEntity<>(chatMessageDto1, status);
    }
}
