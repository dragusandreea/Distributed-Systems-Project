package sd.project.usermanagementservice.dtos;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sd.project.usermanagementservice.entities.UserType;

import java.util.UUID;

import static sd.project.usermanagementservice.utils.ErrorMessages.INVALID_EMAIL_FORMAT_ERROR_MESSAGE;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateDto {
    private UUID id;
    private String name;
    private String username;
    private String password;
    private UserType userType;
}
