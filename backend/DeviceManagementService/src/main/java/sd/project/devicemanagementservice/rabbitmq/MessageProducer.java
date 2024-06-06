package sd.project.devicemanagementservice.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sd.project.devicemanagementservice.dtos.SyncDto;

@Service
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    @Value("${rabbitmq.sync.topic.exchange.name}")
    private String topicExchangeName;
    @Value("${rabbitmq.sync.routing.key}")
    private String routingKey;

    public MessageProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(SyncDto syncDto) throws JsonProcessingException {
        rabbitTemplate.convertAndSend(topicExchangeName, routingKey, objectMapper.writeValueAsString(syncDto));
        System.out.println(syncDto);
    }
}
