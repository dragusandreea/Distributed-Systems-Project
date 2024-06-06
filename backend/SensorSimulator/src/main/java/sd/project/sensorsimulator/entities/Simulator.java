package sd.project.sensorsimulator.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import sd.project.sensorsimulator.services.ReadingDataService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class Simulator {
    private final ReadingDataService readingDataService;
    private final List<MessageProducer> messageProducers;
    private RabbitTemplate rabbitTemplate;
    private final ApplicationContext applicationContext;
    private final ObjectMapper objectMapper;

    @Autowired
    public Simulator(ReadingDataService readingDataService, RabbitTemplate rabbitTemplate, ApplicationContext applicationContext, ObjectMapper objectMapper) {
        this.readingDataService = readingDataService;
        this.rabbitTemplate = rabbitTemplate;
        this.applicationContext = applicationContext;
        this.objectMapper = objectMapper;
        this.messageProducers = new ArrayList<>();
    }

    public void messageProducersListInitialization() {
        ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
        List<String> ids = null;
        try {
            ids = readingDataService.readSensorsIds();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (String id : ids) {
            MessageProducer messageProducer = new MessageProducer(readingDataService, rabbitTemplate, objectMapper);
            messageProducer.setSensorId(id);
            messageProducers.add(messageProducer);
            beanFactory.applyBeanPostProcessorsAfterInitialization(messageProducer, messageProducer.getClass().getCanonicalName());

        }
    }

}
