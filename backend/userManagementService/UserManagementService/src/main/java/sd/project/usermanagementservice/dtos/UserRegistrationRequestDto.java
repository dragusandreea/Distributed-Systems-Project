package sd.project.usermanagementservice.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sd.project.usermanagementservice.entities.UserType;

import static sd.project.usermanagementservice.utils.ErrorMessages.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegistrationRequestDto {
    @NotBlank(message =  BLANK_NAME_MESSAGE)
    private String name;
    @Email(message = INVALID_EMAIL_FORMAT_ERROR_MESSAGE)
    private String username;
    @NotNull(message = NULL_USER_TYPE_MESSAGE)
    private UserType userType;
    @NotBlank(message = NULL_PASSWORD_ERROR_MESSAGE)
    private String password;
}
