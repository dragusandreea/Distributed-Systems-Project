package sd.project.monitoringservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.project.monitoringservice.entities.Device;
import sd.project.monitoringservice.entities.SensorMessage;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface SensorMessageRepository extends JpaRepository<SensorMessage, UUID> {
    List<SensorMessage> findAllByDeviceAndTimestampGreaterThanAndTimestampLessThan(Device device, Timestamp timestampGreater, Timestamp timestampLess);
}
