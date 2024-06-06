package sd.project.devicemanagementservice.services;

import sd.project.devicemanagementservice.entities.Device;

import java.util.List;
import java.util.UUID;

public interface DeviceService {
    Device add(Device device);
    List<Device> getAll();
    Device getById(UUID id);
    List<Device> getByAddress(String address);
    List<Device> getByDescription(String description);
    List<Device> getByHourlyLimit(Integer hourlyLimit);
    List<Device> getByHourlyLimitGreaterThan(Integer hourlyLimit);
    List<Device> getByHourlyLimitLessThan(Integer hourlyLimit);
    List<Device> getByOwnerId(UUID ownerId);
    Device update(Device device);
    void deleteById(UUID id);
    void deleteByOwnerId(UUID ownerId);
}
