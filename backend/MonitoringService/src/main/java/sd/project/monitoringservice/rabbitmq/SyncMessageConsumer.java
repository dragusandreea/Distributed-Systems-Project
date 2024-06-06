package sd.project.monitoringservice.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sd.project.monitoringservice.dtos.DeviceDto;
import sd.project.monitoringservice.dtos.SyncDto;
import sd.project.monitoringservice.entities.Device;
import sd.project.monitoringservice.services.DeviceService;

import static sd.project.monitoringservice.utils.SyncMessages.*;

@Service
public class SyncMessageConsumer {
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final DeviceService deviceService;

    public SyncMessageConsumer(ObjectMapper objectMapper, ModelMapper modelMapper, DeviceService deviceService) {
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.deviceService = deviceService;
    }

    @RabbitListener(queues = {"${rabbitmq.sync.queue.name}"})
    public void consume(String message) throws JsonProcessingException {
        String syncMessage = messageProcessing(message);
        System.out.println(syncMessage);
    }

    private String messageProcessing(String message) throws JsonProcessingException {
        SyncDto syncDto = objectMapper.readValue(message, SyncDto.class);
        DeviceDto deviceDto = syncDto.getDeviceDto();
        String methodType = syncDto.getMethodType();
        if(methodType.equals("CREATE")) {
            Device device = modelMapper.map(deviceDto, Device.class);
            Device savedDevice = deviceService.add(device);
            return CREATE_MESSAGE.formatted(savedDevice);
        }
        if(methodType.equals("UPDATE")) {
            Device device = modelMapper.map(deviceDto, Device.class);
            Device updatedDevice = deviceService.update(device);
            return UPDATE_MESSAGE.formatted(updatedDevice);
        }
        if(methodType.equals("DELETE_BY_ID")) {
            Device device = modelMapper.map(deviceDto, Device.class);
            deviceService.deleteById(device.getId());
            return DELETE_BY_ID_MESSAGE.formatted(device.getId());
        }
        if(methodType.equals("DELETE_BY_OWNER")) {
            Device device = modelMapper.map(deviceDto, Device.class);
            deviceService.deleteByOwnerId(device.getId());
            return DELETE_BY_OWNER_MESSAGE.formatted(device.getOwnerId());
        }
        return null;
    }
}
