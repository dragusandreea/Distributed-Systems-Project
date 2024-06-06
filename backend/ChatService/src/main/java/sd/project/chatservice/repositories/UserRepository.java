package sd.project.chatservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.project.chatservice.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    List<User> findByName(String name);
    void deleteById(UUID uid);
}
