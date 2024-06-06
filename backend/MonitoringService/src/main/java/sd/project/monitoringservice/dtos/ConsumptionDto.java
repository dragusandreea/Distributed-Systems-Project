package sd.project.monitoringservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConsumptionDto {
    private UUID id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "GMT+2")
    private Timestamp timestamp;
    private float hourlyConsumption;
    private DeviceDto deviceDto;
}
