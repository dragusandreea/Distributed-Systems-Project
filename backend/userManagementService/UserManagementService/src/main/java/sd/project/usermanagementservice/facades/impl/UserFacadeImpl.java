package sd.project.usermanagementservice.facades.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import sd.project.usermanagementservice.dtos.*;
import sd.project.usermanagementservice.entities.User;
import sd.project.usermanagementservice.entities.UserType;
import sd.project.usermanagementservice.facades.UserFacade;
import sd.project.usermanagementservice.rabbitmq.MessageProducer;
import sd.project.usermanagementservice.security.AuthenticationService;
import sd.project.usermanagementservice.services.UserService;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserFacadeImpl implements UserFacade {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final ModelMapper modelMapper;
    private final MessageProducer messageProducer;

    public UserFacadeImpl(UserService userService, AuthenticationService authenticationService, ModelMapper modelMapper, MessageProducer messageProducer) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.modelMapper = modelMapper;
        this.messageProducer = messageProducer;
    }

    @Override
    public AuthResponse register(UserRegistrationRequestDto userRegistrationRequestDto) {
        AuthResponse authResponse = authenticationService.register(userRegistrationRequestDto);
        User savedUser = userService.getByUsername(userRegistrationRequestDto.getUsername());
        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
        syncCreate(savedUserDto);
        return authResponse;
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
    }

    @Override
    public UserDto create(UserRegistrationRequestDto userRegistrationRequestDto) {
        User user = modelMapper.map(userRegistrationRequestDto, User.class);
        User savedUser = userService.add(user);
        UserDto userDtoSaved = modelMapper.map(savedUser, UserDto.class);
        syncCreate(userDtoSaved);
        return userDtoSaved;
    }

    @Override
    public List<UserDto> getAll() {
        return userService.getAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(UUID id) {
        User user = userService.getById(id);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getByUsername(String username) {
        User user = userService.getByUsername(username);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getByName(String name) {
        return userService.getByName(name)
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getByUserType(UserType userType) {
        return userService.getByUserType(userType)
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserUpdateDto update(UserUpdateDto userUpdateDto) {
        User user = modelMapper.map(userUpdateDto, User.class);
        User updatedUser = userService.update(user);
        UserUpdateDto userUpdateDtoSaved = modelMapper.map(updatedUser, UserUpdateDto.class);
        syncUpdate(userUpdateDtoSaved);
        return userUpdateDtoSaved;
    }

    @Override
    public Map<String, Object> delete(UUID id) {
        userService.deleteById(id);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Deleted successfully user with id = " + id);
        syncDelete(id);
        return body;
    }

    private SyncDto buildSyncDto(UserDto userDto, String methodType) {
        SyncDto syncDto = new SyncDto();
        syncDto.setUserDto(userDto);
        syncDto.setMethodType(methodType);
        return syncDto;
    }

    private void syncCreate(UserDto userDto) {
        try {
            messageProducer.sendMessage(buildSyncDto(userDto, "CREATE"));
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    private void syncUpdate(UserUpdateDto userUpdateDto) {
        try {
            UserDto userDto = modelMapper.map(userUpdateDto, UserDto.class);
            messageProducer.sendMessage(buildSyncDto(userDto, "UPDATE"));
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    private void syncDelete(UUID id) {
        try {
            UserDto userDto = new UserDto();
            userDto.setId(id);
            messageProducer.sendMessage(buildSyncDto(userDto, "DELETE_BY_ID"));
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }
}
