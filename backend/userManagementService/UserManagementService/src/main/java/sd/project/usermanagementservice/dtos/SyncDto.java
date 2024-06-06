package sd.project.usermanagementservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SyncDto {
    private String methodType;
    private UserDto userDto;
}
