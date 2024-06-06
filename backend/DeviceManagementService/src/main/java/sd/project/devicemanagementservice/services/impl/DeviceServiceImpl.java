package sd.project.devicemanagementservice.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import sd.project.devicemanagementservice.entities.Device;
import sd.project.devicemanagementservice.exceptions.DeviceNotFoundException;
import sd.project.devicemanagementservice.repositories.DeviceRepository;
import sd.project.devicemanagementservice.services.DeviceService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static sd.project.devicemanagementservice.utils.ErrorMessages.*;

@Transactional
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
    public List<Device> getAll() {
        return deviceRepository.findAll();
    }

    @Override
    public Device getById(UUID id) {
        return deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException(DEVICE_NOT_FOUND_BY_ID_MESSAGE.formatted(id.toString())));
    }

    @Override
    public List<Device> getByAddress(String address) {
        List<Device> devices = deviceRepository.findByAddress(address);
        if(devices.isEmpty()) {
            throw new DeviceNotFoundException(DEVICE_NOT_FOUND_BY_ADDRESS_MESSAGE.formatted(address));
        } else {
            return devices;
        }
    }

    @Override
    public List<Device> getByDescription(String description) {
        List<Device> devices = deviceRepository.findByDescription(description);
        if(devices.isEmpty()) {
            throw new DeviceNotFoundException(DEVICE_NOT_FOUND_BY_DESCRIPTION_MESSAGE.formatted(description));
        } else {
            return devices;
        }
    }

    @Override
    public List<Device> getByHourlyLimit(Integer hourlyLimit) {
        List<Device> devices = deviceRepository.findByHourlyEnergyConsumptionLimit(hourlyLimit);
        if(devices.isEmpty()) {
            throw new DeviceNotFoundException(DEVICE_NOT_FOUND_BY_HOURLY_LIMIT_MESSAGE.formatted(hourlyLimit));
        } else {
            return devices;
        }
    }

    @Override
    public List<Device> getByHourlyLimitGreaterThan(Integer hourlyLimit) {
        List<Device> devices = deviceRepository.findByHourlyEnergyConsumptionLimitIsGreaterThan(hourlyLimit);
        if(devices.isEmpty()) {
            throw new DeviceNotFoundException(DEVICE_NOT_FOUND_BY_HOURLY_LIMIT_GREATER_THAN_MESSAGE.formatted(hourlyLimit));
        } else {
            return devices;
        }
    }

    @Override
    public List<Device> getByHourlyLimitLessThan(Integer hourlyLimit) {
        List<Device> devices = deviceRepository.findByHourlyEnergyConsumptionLimitIsLessThan(hourlyLimit);
        if(devices.isEmpty()) {
            throw new DeviceNotFoundException(DEVICE_NOT_FOUND_BY_HOURLY_LIMIT_LESS_THAN_MESSAGE.formatted(hourlyLimit));
        } else {
            return devices;
        }
    }

    @Override
    public List<Device> getByOwnerId(UUID ownerId) {
        List<Device> devices = deviceRepository.findByOwnerId(ownerId);
        if(devices.isEmpty()) {
            throw new DeviceNotFoundException(DEVICE_NOT_FOUND_BY_OWNER_ID_MESSAGE.formatted(ownerId));
        } else {
            return devices;
        }
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
            deviceRepository.save(device);
        }
    }
}
