package sd.project.monitoringservice.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import sd.project.monitoringservice.dtos.MessageSensorDto;
import sd.project.monitoringservice.dtos.PrivateMessageDto;
import sd.project.monitoringservice.entities.Consumption;
import sd.project.monitoringservice.entities.Device;
import sd.project.monitoringservice.entities.SensorMessage;
import sd.project.monitoringservice.services.ConsumptionService;
import sd.project.monitoringservice.services.DeviceService;
import sd.project.monitoringservice.services.SensorMessageService;

import java.sql.Timestamp;;
import java.util.List;

import static sd.project.monitoringservice.utils.NotificationMessages.LIMIT_EXCEEDED_MESSAGE;

@Service
public class SensorMessageConsumer {
    private final String SOCKET_URL = "/notification/";
    private final ObjectMapper objectMapper;
    private final DeviceService deviceService;
    private final SensorMessageService sensorMessageService;
    private final ConsumptionService consumptionService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public SensorMessageConsumer(ObjectMapper objectMapper, DeviceService deviceService, SensorMessageService sensorMessageService, ConsumptionService consumptionService, SimpMessagingTemplate simpMessagingTemplate) {
        this.objectMapper = objectMapper;
        this.deviceService = deviceService;
        this.sensorMessageService = sensorMessageService;
        this.consumptionService = consumptionService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String message) throws JsonProcessingException {
        SensorMessage sensorMessage = messageProcessing(message);
        computeHourlyConsumption(sensorMessage);
        System.out.println(sensorMessage);
    }

    private SensorMessage messageProcessing(String message) throws JsonProcessingException {
        MessageSensorDto messageSensorDto = objectMapper.readValue(message, MessageSensorDto.class);
        Device device = deviceService.getById(messageSensorDto.getDeviceId());
        SensorMessage sensorMessage = objectMapper.convertValue(messageSensorDto, SensorMessage.class);
        sensorMessage.setDevice(device);
        return sensorMessageService.add(sensorMessage);
    }

    private void computeHourlyConsumption(SensorMessage sensorMessage) {
        Timestamp timestamp1HourAgo= Timestamp.valueOf(sensorMessage.getTimestamp().toLocalDateTime().minusHours(1L).withMinute(0).withSecond(0).withNano(0));
        List<Consumption> consumptionList = consumptionService.getAllByDeviceIdAndTimestampIsAfterAndTimestampIsBefore(sensorMessage.getDevice().getId(), timestamp1HourAgo, sensorMessage.getTimestamp());
        for(Consumption consumption: consumptionList) {
            consumption.setHourlyConsumption(consumption.getHourlyConsumption() + sensorMessage.getValue());
            consumptionService.update(consumption);
            verifyHourlyConsumptionLimit(sensorMessage.getDevice(), consumption.getHourlyConsumption());
            return;
        }

        Consumption consumption = new Consumption();
        consumption.setDevice(sensorMessage.getDevice());
        consumption.setHourlyConsumption(sensorMessage.getValue());
        consumption.setTimestamp(sensorMessage.getTimestamp());
        consumptionService.add(consumption);
        verifyHourlyConsumptionLimit(sensorMessage.getDevice(), sensorMessage.getValue());
    }

    private void verifyHourlyConsumptionLimit(Device device, float totalHourlyConsumption) {
        if(device.getHourlyEnergyConsumptionLimit() < totalHourlyConsumption) {
            if (device.getOwnerId() != null) {
                PrivateMessageDto privateMessageDto = new PrivateMessageDto();
                privateMessageDto.setOwnerId(device.getOwnerId());
                privateMessageDto.setMessage(LIMIT_EXCEEDED_MESSAGE.formatted(device.getDescription(), device.getHourlyEnergyConsumptionLimit(), totalHourlyConsumption));
                simpMessagingTemplate.convertAndSend(SOCKET_URL + device.getOwnerId().toString(), privateMessageDto);
            }
        }
    }
}
