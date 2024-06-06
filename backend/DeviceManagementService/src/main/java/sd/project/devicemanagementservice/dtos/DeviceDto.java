package sd.project.devicemanagementservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static sd.project.devicemanagementservice.utils.ErrorMessages.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeviceDto {
    private UUID id;
    @NotBlank(message = BLANK_DESCRIPTION_ERROR_MESSAGE)
    private String description;
    @NotBlank(message = BLANK_ADDRESS_ERROR_MESSAGE)
    private String address;
    @NotNull(message = NULL_HOURLY_LIMIT_ERROR_MESSAGE)
    private Integer hourlyEnergyConsumptionLimit;
    private UUID ownerId;
}
