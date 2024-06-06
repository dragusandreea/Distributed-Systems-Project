package sd.project.usermanagementservice.services.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sd.project.usermanagementservice.entities.User;
import sd.project.usermanagementservice.entities.UserType;
import sd.project.usermanagementservice.exceptions.UserNotFoundException;
import sd.project.usermanagementservice.repositories.UserRepository;
import sd.project.usermanagementservice.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static sd.project.usermanagementservice.utils.ErrorMessages.*;

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
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_BY_USERNAME_MESSAGE.formatted(username)));
    }

    @Override
    public List<User> getByName(String name) {
       List<User> users = userRepository.findByName(name);
       if(users.isEmpty()) {
           throw new UserNotFoundException(USER_NOT_FOUND_BY_NAME_MESSAGE.formatted(name));
       } else {
           return users;
       }
    }

    @Override
    public List<User> getByUserType(UserType userType) {
        return userRepository.findByUserType(userType);
    }

    @Override
    public User update(User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException(USER_NOT_FOUND_BY_ID_MESSAGE.formatted(user.getId().toString()));
        } else {
            if(user.getUserType() == null) {
                user.setUserType(optionalUser.get().getUserType());
            }
            if(user.getName() == null || user.getName().isEmpty()) {
                user.setName(optionalUser.get().getName());
            }
            if(user.getUsername() == null || user.getUsername().isEmpty()) {
                user.setUsername(optionalUser.get().getUsername());
            }
            if(user.getPassword() == null || user.getPassword().isEmpty()) {
                user.setPassword(optionalUser.get().getPassword());
            }else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            return userRepository.save(user);
        }
    }

    @Override
    public void deleteById(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException(USER_NOT_FOUND_BY_ID_MESSAGE.formatted(id.toString()));
        } else {
            userRepository.deleteById(id);
        }
    }
}