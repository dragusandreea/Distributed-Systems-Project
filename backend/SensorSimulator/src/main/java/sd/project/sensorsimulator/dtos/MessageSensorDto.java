package sd.project.sensorsimulator.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageSensorDto implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "GMT")
    private Timestamp timestamp;
    private String deviceId;
    private String value;
}
