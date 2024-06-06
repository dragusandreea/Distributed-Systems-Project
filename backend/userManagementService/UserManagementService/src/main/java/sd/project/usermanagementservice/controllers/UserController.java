package sd.project.usermanagementservice.controllers;

import org.springframework.web.bind.annotation.*;
import sd.project.usermanagementservice.dtos.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import sd.project.usermanagementservice.entities.UserType;
import sd.project.usermanagementservice.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.UUID;
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public interface UserController {
    @PostMapping("/register")
    ResponseEntity<AuthResponse> register(@Valid @RequestBody UserRegistrationRequestDto request);

    @PostMapping("/login")
    ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) throws UserNotFoundException;

    @PostMapping("/create")
    ResponseEntity<UserDto> create(@Valid @RequestBody UserRegistrationRequestDto userRegistrationRequestDto);

    @GetMapping("/read/getAll")
    ResponseEntity<List<UserDto>> getAll();

    @GetMapping("/read/getById/{id}")
    ResponseEntity<UserDto> getById(@PathVariable UUID id);

    @GetMapping("/read/getByUsername/{username}")
    ResponseEntity<UserDto> getByUsername(@PathVariable String username);

    @GetMapping("/read/getByName/{name}")
    ResponseEntity<List<UserDto>> getByName(@PathVariable String name);

    @GetMapping("/read/getByUserType/{userType}")
    ResponseEntity<List<UserDto>> getByUserType(@PathVariable UserType userType);

    @PutMapping("/update")
    ResponseEntity<UserUpdateDto> update(@RequestBody UserUpdateDto userUpdateDto);

    @DeleteMapping("/delete/deleteById/{id}")
    ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id);
}
