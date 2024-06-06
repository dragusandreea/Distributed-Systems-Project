package sd.project.usermanagementservice.services;

import sd.project.usermanagementservice.entities.User;
import sd.project.usermanagementservice.entities.UserType;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User add(User user);
    List<User> getAll();
    User getById(UUID id);
    User getByUsername(String username);
    List<User> getByName(String name);
    List<User> getByUserType(UserType userType);
    User update(User user);
    void deleteById(UUID id);
}
