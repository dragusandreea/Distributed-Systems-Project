package sd.project.chatservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatMessageDto {
    private UUID id;
    private UUID senderUid;
    private UUID receiverUid;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "GMT+2")
    private Timestamp sentTimestamp;
    private boolean seen;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "GMT+2")
    private Timestamp seenTimestamp;
}
