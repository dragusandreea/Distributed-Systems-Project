package sd.project.monitoringservice.services.impl;

import org.springframework.stereotype.Service;
import sd.project.monitoringservice.entities.Device;
import sd.project.monitoringservice.exception.DeviceNotFoundException;
import sd.project.monitoringservice.repositories.DeviceRepository;
import sd.project.monitoringservice.services.DeviceService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static sd.project.monitoringservice.utils.ExceptionMessages.DEVICE_NOT_FOUND_BY_ID_MESSAGE;

@Service
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }
    @Override
    public Device add(Device device) {
        return deviceRepository.save(device);
    }
    @Override
    public Device getById(UUID id) {
        return deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException(DEVICE_NOT_FOUND_BY_ID_MESSAGE.formatted(id.toString())));
    }

    @Override
    public List<Device> getAll() {
        return deviceRepository.findAll();
    }

    @Override
    public Device update(Device device) {
        Optional<Device> deviceOptional = deviceRepository.findById(device.getId());
        if(deviceOptional.isEmpty()) {
            throw new DeviceNotFoundException(DEVICE_NOT_FOUND_BY_ID_MESSAGE.formatted(device.getId()));
        } else {
            if(device.getDescription() == null || device.getDescription().isEmpty()) {
                device.setDescription(deviceOptional.get().getDescription());
            }
            if(device.getAddress() == null || device.getAddress().isEmpty()) {
                device.setAddress(deviceOptional.get().getAddress());
            }
            if(device.getOwnerId() == null) {
                device.setOwnerId(deviceOptional.get().getOwnerId());
            }
            if(device.getHourlyEnergyConsumptionLimit() == null) {
                device.setHourlyEnergyConsumptionLimit(deviceOptional.get().getHourlyEnergyConsumptionLimit());
            }
            return deviceRepository.save(device);
        }
    }

    @Override
    public void deleteById(UUID id) {
        Optional<Device> deviceOptional= deviceRepository.findById(id);
        if(deviceOptional.isEmpty()) {
            throw new DeviceNotFoundException(DEVICE_NOT_FOUND_BY_ID_MESSAGE.formatted(id.toString()));
        } else {
            deviceRepository.deleteById(id);
        }
    }

    @Override
    public void deleteByOwnerId(UUID ownerId) {
        List<Device> devices = deviceRepository.findByOwnerId(ownerId);
        for(Device device: devices) {
            device.setOwnerId(null);
            Device device1 = deviceRepository.save(device);
            System.out.println(device1);
        }
    }
}
