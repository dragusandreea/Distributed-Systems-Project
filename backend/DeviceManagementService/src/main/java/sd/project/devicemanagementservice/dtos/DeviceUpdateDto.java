package sd.project.devicemanagementservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeviceUpdateDto {
    private UUID id;
    private String description;
    private String address;
    private Integer hourlyEnergyConsumptionLimit;
    private UUID ownerId;
}
