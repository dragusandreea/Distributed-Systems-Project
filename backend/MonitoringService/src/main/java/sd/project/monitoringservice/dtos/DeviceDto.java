package sd.project.monitoringservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeviceDto {
    private UUID id;
    private String description;
    private String address;
    private Integer hourlyEnergyConsumptionLimit;
    private UUID ownerId;
}
