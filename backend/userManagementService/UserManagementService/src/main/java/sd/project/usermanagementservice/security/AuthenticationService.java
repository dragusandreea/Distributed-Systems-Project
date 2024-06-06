package sd.project.usermanagementservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sd.project.usermanagementservice.dtos.AuthResponse;
import sd.project.usermanagementservice.dtos.LoginRequest;
import sd.project.usermanagementservice.dtos.UserRegistrationRequestDto;
import sd.project.usermanagementservice.entities.UserType;
import sd.project.usermanagementservice.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import sd.project.usermanagementservice.entities.User;
import sd.project.usermanagementservice.exceptions.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(UserRegistrationRequestDto request) {
        var jwtToken = "";
        var user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(request.getPassword())
                .userType(request.getUserType())
                .build();

        User savedUser = userService.add(user);

        jwtToken = jwtService.generateToken(savedUser);
        return AuthResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse login(LoginRequest request) throws UserNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User foundUser = userService.getByUsername(request.getUsername());

        var jwtToken = jwtService.generateToken(foundUser);

        return AuthResponse
                .builder()
                .token(jwtToken)
                .build();

    }
}
