package sd.project.usermanagementservice.controllers.impl;

import sd.project.usermanagementservice.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sd.project.usermanagementservice.controllers.UserController;
import sd.project.usermanagementservice.entities.UserType;
import sd.project.usermanagementservice.exceptions.UserNotFoundException;
import sd.project.usermanagementservice.facades.UserFacade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class UserControllerImpl implements UserController {
    private final UserFacade userFacade;

    public UserControllerImpl(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @Override
    public ResponseEntity<AuthResponse> register(UserRegistrationRequestDto request) {
        return new ResponseEntity<>(userFacade.register(request), CREATED);
    }

    @Override
    public ResponseEntity<AuthResponse> login(LoginRequest request) throws UserNotFoundException {
        return new ResponseEntity<>(userFacade.login(request), OK);
    }

    @Override
    public ResponseEntity<UserDto> create(UserRegistrationRequestDto userRegistrationRequestDto) {
        return new ResponseEntity<>(userFacade.create(userRegistrationRequestDto), CREATED);
    }

    @Override
    public ResponseEntity<List<UserDto>> getAll() {
        return new ResponseEntity<>(userFacade.getAll(), OK);
    }

    @Override
    public ResponseEntity<UserDto> getById(UUID id) {
        return new ResponseEntity<>(userFacade.getById(id), OK);
    }

    @Override
    public ResponseEntity<UserDto> getByUsername(String username) {
        return new ResponseEntity<>(userFacade.getByUsername(username), OK);
    }

    @Override
    public ResponseEntity<List<UserDto>> getByName(String name) {
        return new ResponseEntity<>(userFacade.getByName(name), OK);
    }

    @Override
    public ResponseEntity<List<UserDto>> getByUserType(UserType userType) {
        return new ResponseEntity<>(userFacade.getByUserType(userType), OK);
    }

    @Override
    public ResponseEntity<UserUpdateDto> update(UserUpdateDto userUpdateDto) {
        return new ResponseEntity<>(userFacade.update(userUpdateDto), OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> delete(UUID id) {
        return new ResponseEntity<>(userFacade.delete(id), OK);
    }
}
