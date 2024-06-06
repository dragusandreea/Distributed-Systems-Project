package sd.project.usermanagementservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sd.project.usermanagementservice.entities.UserType;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private UUID id;
    private String name;
    private String username;
    private UserType userType;
    private String password;
}
