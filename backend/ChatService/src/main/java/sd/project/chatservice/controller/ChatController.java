package sd.project.chatservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import sd.project.chatservice.dtos.ChatMessageDto;
import sd.project.chatservice.dtos.NotificationMessageDto;
import sd.project.chatservice.entities.ChatMessage;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
public interface ChatController {
    @MessageMapping("/private-chat/{adminUid}/{clientUid}")
    void processMessage(@Payload ChatMessageDto chatMessageDto) throws Exception;
    @MessageMapping("/private-chat/typing/{admin}/{client}")
    void processMessage2(@Payload String uid);
    @GetMapping("/read/getAllBySenderIdAndReceiverId/{senderId}/{receiverId}")
    ResponseEntity<List<ChatMessageDto>> getAllBySenderIdAndReceiverId(@RequestHeader("Authorization") String token, @PathVariable UUID senderId, @PathVariable UUID receiverId);

    @GetMapping("/read/getAllBetweenSenderIdAndReceiverId/{senderId}/{receiverId}")
    ResponseEntity<List<ChatMessageDto>> getAllBetweenSenderIdAndReceiverId(@RequestHeader("Authorization") String token, @PathVariable UUID senderId, @PathVariable UUID receiverId);
    @PutMapping("/update")
    ResponseEntity<ChatMessageDto> updateSeenChatMessage(@RequestHeader("Authorization") String token, @RequestBody ChatMessageDto chatMessageDto);
}
