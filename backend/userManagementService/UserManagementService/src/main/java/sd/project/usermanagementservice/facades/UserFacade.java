package sd.project.usermanagementservice.facades;

import sd.project.usermanagementservice.dtos.*;
import sd.project.usermanagementservice.entities.UserType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserFacade {
    AuthResponse register(UserRegistrationRequestDto userRegistrationRequestDto);
    AuthResponse login(LoginRequest loginRequest);
    UserDto create(UserRegistrationRequestDto userRegistrationRequestDto);
    List<UserDto> getAll();
    UserDto getById(UUID id);
    UserDto getByUsername(String username);
    List<UserDto> getByName(String name);
    List<UserDto> getByUserType(UserType userType);
    UserUpdateDto update(UserUpdateDto userUpdateDto);
    Map<String, Object> delete(UUID id);
}
