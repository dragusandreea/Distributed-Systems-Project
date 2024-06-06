package sd.project.sensorsimulator.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sd.project.sensorsimulator.dtos.MessageSensorDto;
import sd.project.sensorsimulator.services.ReadingDataService;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Scope("prototype")
@Data
public class MessageProducer {
    private final ReadingDataService readingDataService;
    private String sensorId;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    @Value("${rabbitmq.topic.exchange.name}")
    private String topicExchangeName;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Autowired
    public MessageProducer(ReadingDataService readingDataService, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.readingDataService = readingDataService;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(cron = "#{@cronReadingSensorData}")
    public void read() throws IOException {
        String value = readingDataService.readSensorData();
        MessageSensorDto messageSensorDto = createMessage(this.sensorId, value);
        sendMessage(messageSensorDto);
    }

    public void sendMessage(MessageSensorDto messageSensorDto) throws JsonProcessingException {
        rabbitTemplate.setRoutingKey("sensor_routing_key");
        rabbitTemplate.setExchange("sensor_topic_exchange");
        rabbitTemplate.convertAndSend("sensor_topic_exchange", "sensor_routing_key", objectMapper.writeValueAsString(messageSensorDto));
        System.out.println( objectMapper.writeValueAsString(messageSensorDto));
    }

    public MessageSensorDto createMessage(String deviceId, String value) {
        MessageSensorDto messageSensorDto = new MessageSensorDto();
        messageSensorDto.setTimestamp(Timestamp.valueOf(LocalDateTime.now().plusHours(2)));
        messageSensorDto.setDeviceId(deviceId);
        messageSensorDto.setValue(value);
        return messageSensorDto;
    }


}
