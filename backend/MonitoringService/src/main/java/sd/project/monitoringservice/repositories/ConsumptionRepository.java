package sd.project.monitoringservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.project.monitoringservice.entities.Consumption;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface ConsumptionRepository extends JpaRepository<Consumption, UUID> {
    List<Consumption> findAllByDevice_OwnerId(UUID ownerId);
    List<Consumption> findAllByDevice_IdAndTimestampIsAfterAndTimestampIsBefore(UUID deviceId, Timestamp timestampAfter, Timestamp timestampBefore);
}
