package sd.project.chatservice.services;

import sd.project.chatservice.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User add(User user);
    List<User> getAll();
    User getById(UUID id);
    User update(User user);
    void deleteById(UUID id);
}
