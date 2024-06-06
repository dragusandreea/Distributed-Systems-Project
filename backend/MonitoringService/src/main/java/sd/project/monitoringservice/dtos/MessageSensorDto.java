package sd.project.monitoringservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

import static sd.project.monitoringservice.utils.ExceptionMessages.BLANK_DEVICE_ID_ERROR_MESSAGE;
import static sd.project.monitoringservice.utils.ExceptionMessages.BLANK_TIMESTAMP_ERROR_MESSAGE;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageSensorDto  {
    @NotBlank(message = BLANK_TIMESTAMP_ERROR_MESSAGE)
    private Timestamp timestamp;
    @NotBlank(message = BLANK_DEVICE_ID_ERROR_MESSAGE)
    private UUID deviceId;
    private Float value;
    public MessageSensorDto (@JsonProperty("timestamp") String timestamp, @JsonProperty("deviceId") String deviceId, @JsonProperty("value") String value) {
        this.timestamp = Timestamp.valueOf(timestamp);
        this.deviceId = UUID.fromString(deviceId);
        this.value = Float.parseFloat(value);
    }
}
