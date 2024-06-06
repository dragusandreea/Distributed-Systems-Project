package sd.project.usermanagementservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sd.project.usermanagementservice.entities.User;
import sd.project.usermanagementservice.entities.UserType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    List<User> findByName(String name);
    List<User> findByUserType(UserType userType);
    void deleteById(UUID id);

}
