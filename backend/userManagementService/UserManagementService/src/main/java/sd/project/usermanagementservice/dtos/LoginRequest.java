package sd.project.usermanagementservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static sd.project.usermanagementservice.utils.ErrorMessages.BLANK_PASSWORD_ERROR_MESSAGE;
import static sd.project.usermanagementservice.utils.ErrorMessages.BLANK_USERNAME_ERROR_MESSAGE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = BLANK_USERNAME_ERROR_MESSAGE)
    private String username;
    @NotBlank(message = BLANK_PASSWORD_ERROR_MESSAGE)
    private String password;
}
