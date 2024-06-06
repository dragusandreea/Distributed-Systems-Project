package sd.project.monitoringservice.services;

import sd.project.monitoringservice.entities.Consumption;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ConsumptionService {
    Consumption add(Consumption consumption);
    List<Consumption> getAllByOwnerId(UUID ownerId);
    List<Consumption> getAllByDeviceIdAndTimestampIsAfterAndTimestampIsBefore(UUID deviceId, Timestamp timestampAfter, Timestamp timestampBefore);
    Consumption update(Consumption consumption);
}
