package sd.project.devicemanagementservice.facades.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import sd.project.devicemanagementservice.dtos.DeviceDto;
import sd.project.devicemanagementservice.dtos.DeviceUpdateDto;
import sd.project.devicemanagementservice.dtos.SyncDto;
import sd.project.devicemanagementservice.entities.Device;
import sd.project.devicemanagementservice.facades.DeviceFacade;
import sd.project.devicemanagementservice.rabbitmq.MessageProducer;
import sd.project.devicemanagementservice.services.DeviceService;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class DeviceFacadeImpl implements DeviceFacade {
    private final DeviceService deviceService;
    private final ModelMapper modelMapper;
    private final MessageProducer messageProducer;

    public DeviceFacadeImpl(DeviceService deviceService, ModelMapper modelMapper, MessageProducer messageProducer) {
        this.deviceService = deviceService;
        this.modelMapper = modelMapper;
        this.messageProducer = messageProducer;
    }

    @Override
    public DeviceDto create(DeviceDto deviceDto)  {
        Device device = modelMapper.map(deviceDto, Device.class);
        Device savedDevice = deviceService.add(device);
        DeviceDto deviceDtoSaved = modelMapper.map(savedDevice, DeviceDto.class);
        syncCreate(deviceDtoSaved);
        return deviceDtoSaved;
    }

    @Override
    public List<DeviceDto> getAll() {
        return deviceService.getAll()
                .stream()
                .map(user -> modelMapper.map(user, DeviceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public DeviceDto getById(UUID id) {
        Device device = deviceService.getById(id);
        return modelMapper.map(device, DeviceDto.class);
    }

    @Override
    public List<DeviceDto> getByDescription(String description) {
        return deviceService.getAll()
                .stream()
                .filter(device -> device.getDescription().equals(description))
                .map(device -> modelMapper.map(device, DeviceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DeviceDto> getByAddress(String address) {
        return deviceService.getAll()
                .stream()
                .filter(device -> device.getAddress().equals(address))
                .map(device -> modelMapper.map(device, DeviceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DeviceDto> getByHourlyLimit(Integer hourlyLimit) {
        return deviceService.getAll()
                .stream()
                .filter(device -> device.getHourlyEnergyConsumptionLimit().equals(hourlyLimit))
                .map(device -> modelMapper.map(device, DeviceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DeviceDto> getByHourlyLimitLessThan(Integer hourlyLimit) {
        return deviceService.getAll()
                .stream()
                .filter(device -> device.getHourlyEnergyConsumptionLimit().compareTo(hourlyLimit) < 0)
                .map(device -> modelMapper.map(device, DeviceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DeviceDto> getByHourlyLimitGreaterThan(Integer hourlyLimit) {
        return deviceService.getAll()
                .stream()
                .filter(device -> device.getHourlyEnergyConsumptionLimit().compareTo(hourlyLimit) > 0)
                .map(device -> modelMapper.map(device, DeviceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DeviceDto> getByOwnerId(UUID ownerId) {
        return deviceService.getAll()
                .stream()
                .filter(device -> device.getOwnerId() != null && device.getOwnerId().equals(ownerId))
                .map(device -> modelMapper.map(device, DeviceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public DeviceUpdateDto update(DeviceUpdateDto deviceUpdateDto) {
        Device device = modelMapper.map(deviceUpdateDto, Device.class);
        Device updatedDevice= deviceService.update(device);
        DeviceUpdateDto deviceUpdateDtoSaved = modelMapper.map(updatedDevice, DeviceUpdateDto.class);
        syncUpdate(deviceUpdateDtoSaved);
        return deviceUpdateDtoSaved;
    }

    @Override
    public Map<String, Object> deleteById(UUID id) {
        deviceService.deleteById(id);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Deleted successfully device with id = " + id);
        syncDelete(id);
        return body;
    }

    @Override
    public Map<String, Object> deleteByOwnerId(UUID ownerId) {
        deviceService.deleteByOwnerId(ownerId);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Deleted successfully devices of the owner with id = " + ownerId);
        syncDeleteByOwner(ownerId);
        return body;
    }

    private SyncDto buildSyncDto(DeviceDto deviceDto, String methodType) {
        SyncDto syncDto = new SyncDto();
        syncDto.setDeviceDto(deviceDto);
        syncDto.setMethodType(methodType);
        return syncDto;
    }

    private void syncCreate(DeviceDto deviceDto) {
        try {
            messageProducer.sendMessage(buildSyncDto(deviceDto, "CREATE"));
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    private void syncUpdate(DeviceUpdateDto deviceUpdateDto) {
        try {
            DeviceDto deviceDto = modelMapper.map(deviceUpdateDto, DeviceDto.class);
            messageProducer.sendMessage(buildSyncDto(deviceDto, "UPDATE"));
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    private void syncDelete(UUID id) {
        try {
            DeviceDto deviceDto = new DeviceDto();
            deviceDto.setId(id);
            messageProducer.sendMessage(buildSyncDto(deviceDto, "DELETE_BY_ID"));
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    private void syncDeleteByOwner(UUID ownerId) {
        try {
            DeviceDto deviceDto = new DeviceDto();
            deviceDto.setOwnerId(ownerId);
            messageProducer.sendMessage(buildSyncDto(deviceDto, "DELETE_BY_OWNER"));
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }
}
