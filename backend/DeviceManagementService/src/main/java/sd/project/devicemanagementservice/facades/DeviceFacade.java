package sd.project.devicemanagementservice.facades;

import com.fasterxml.jackson.core.JsonProcessingException;
import sd.project.devicemanagementservice.dtos.DeviceDto;
import sd.project.devicemanagementservice.dtos.DeviceUpdateDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface DeviceFacade {
    DeviceDto create(DeviceDto deviceDto) throws JsonProcessingException;
    List<DeviceDto> getAll();
    DeviceDto getById(UUID id);
    List<DeviceDto> getByDescription(String description);
    List<DeviceDto> getByAddress(String address);
    List<DeviceDto> getByHourlyLimit(Integer hourlyLimit);
    List<DeviceDto> getByHourlyLimitLessThan(Integer hourlyLimit);
    List<DeviceDto> getByHourlyLimitGreaterThan(Integer hourlyLimit);
    List<DeviceDto> getByOwnerId(UUID ownerId);
    DeviceUpdateDto update(DeviceUpdateDto deviceUpdateDto);
    Map<String, Object> deleteById(UUID id);
    Map<String, Object> deleteByOwnerId(UUID ownerId);
}
