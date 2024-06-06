package sd.project.monitoringservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrivateMessageDto {
    private UUID ownerId;
    private String message;
}
