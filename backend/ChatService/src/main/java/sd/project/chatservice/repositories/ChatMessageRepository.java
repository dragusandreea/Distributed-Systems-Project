package sd.project.chatservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.project.chatservice.entities.ChatMessage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {
    Optional<ChatMessage> findById(UUID id);
    List<ChatMessage> findAllBySenderUidAndReceiverUid(UUID senderUid, UUID receiverUid);
}
