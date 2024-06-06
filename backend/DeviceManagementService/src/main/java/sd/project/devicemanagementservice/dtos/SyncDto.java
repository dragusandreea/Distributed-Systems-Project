package sd.project.devicemanagementservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SyncDto {
    private String methodType;
    private DeviceDto deviceDto;
}
