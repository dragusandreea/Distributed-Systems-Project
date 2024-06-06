package sd.project.monitoringservice.services;

import sd.project.monitoringservice.entities.Device;
import sd.project.monitoringservice.entities.SensorMessage;

import java.sql.Timestamp;
import java.util.List;

public interface SensorMessageService {
    SensorMessage add(SensorMessage sensorMessage);
    List<SensorMessage> getAll();
    List<SensorMessage> getAllByDeviceAndTimestampGreaterThanAndTimestampLessThan(Device device, Timestamp timestampGreater, Timestamp timestampLess);
}
