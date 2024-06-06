package sd.project.monitoringservice.services.impl;

import org.springframework.stereotype.Service;
import sd.project.monitoringservice.entities.Device;
import sd.project.monitoringservice.entities.SensorMessage;
import sd.project.monitoringservice.repositories.SensorMessageRepository;
import sd.project.monitoringservice.services.SensorMessageService;

import java.sql.Timestamp;
import java.util.List;

@Service
public class SensorMessageServiceImpl implements SensorMessageService {
    private final SensorMessageRepository sensorMessageRepository;

    public SensorMessageServiceImpl(SensorMessageRepository sensorMessageRepository) {
        this.sensorMessageRepository = sensorMessageRepository;
    }

    @Override
    public SensorMessage add(SensorMessage sensorMessage) {
        return sensorMessageRepository.save(sensorMessage);
    }

    @Override
    public List<SensorMessage> getAll() {
        return sensorMessageRepository.findAll();
    }

    @Override
    public List<SensorMessage> getAllByDeviceAndTimestampGreaterThanAndTimestampLessThan(Device device, Timestamp timestampGreater, Timestamp timestampLess) {
        return sensorMessageRepository.findAllByDeviceAndTimestampGreaterThanAndTimestampLessThan(device, timestampGreater, timestampLess);
    }
}
