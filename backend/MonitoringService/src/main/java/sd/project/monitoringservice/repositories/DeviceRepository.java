package sd.project.monitoringservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.project.monitoringservice.entities.Device;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    List<Device> findByOwnerId(UUID ownerId);
}
