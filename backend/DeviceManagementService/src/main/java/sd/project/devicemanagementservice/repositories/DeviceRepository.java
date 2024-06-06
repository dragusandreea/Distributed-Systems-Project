package sd.project.devicemanagementservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.project.devicemanagementservice.entities.Device;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository  extends JpaRepository<Device, UUID> {
    List<Device> findByAddress(String address);
    List<Device> findByDescription(String description);
    List<Device> findByHourlyEnergyConsumptionLimit(Integer hourlyEnergyConsumptionLimit);
    List<Device> findByHourlyEnergyConsumptionLimitIsGreaterThan(Integer hourlyEnergyConsumptionLimit);
    List<Device> findByHourlyEnergyConsumptionLimitIsLessThan(Integer hourlyEnergyConsumptionLimit);
    List<Device> findByOwnerId(UUID id);
    void deleteById(UUID id);
    void deleteByOwnerId(UUID ownerId);
}
