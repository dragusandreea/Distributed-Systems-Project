package sd.project.monitoringservice.services;

import sd.project.monitoringservice.entities.Device;

import java.util.List;
import java.util.UUID;

public interface DeviceService {
    Device add(Device device);
    Device getById(UUID id);
    List<Device> getAll();
    Device update(Device device);
    void deleteById(UUID id);
    void deleteByOwnerId(UUID ownerId);
}
