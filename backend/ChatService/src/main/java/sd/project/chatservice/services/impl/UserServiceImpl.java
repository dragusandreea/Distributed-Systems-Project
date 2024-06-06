package sd.project.chatservice.services.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sd.project.chatservice.entities.User;
import sd.project.chatservice.exceptions.UserNotFoundException;
import sd.project.chatservice.repositories.UserRepository;
import sd.project.chatservice.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static sd.project.chatservice.utils.ErrorMessages.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_BY_ID_MESSAGE.formatted(id.toString())));
    }

    @Override
    public User update(User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(USER_NOT_FOUND_BY_ID_MESSAGE.formatted(user.getId().toString()));
        } else {
            if (user.getUserType() == null) {
                user.setUserType(optionalUser.get().getUserType());
            }
            if (user.getName() == null || user.getName().isEmpty()) {
                user.setName(optionalUser.get().getName());
            }
            if (user.getUsername() == null || user.getUsername().isEmpty()) {
                user.setUsername(optionalUser.get().getUsername());
            }
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                user.setPassword(optionalUser.get().getPassword());
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            return userRepository.save(user);
        }
    }

    @Override
    public void deleteById(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(USER_NOT_FOUND_BY_ID_MESSAGE.formatted(id.toString()));
        } else {
            userRepository.deleteById(id);
        }
    }
}
