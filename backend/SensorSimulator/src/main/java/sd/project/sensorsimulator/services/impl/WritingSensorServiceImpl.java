package sd.project.sensorsimulator.services.impl;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import sd.project.sensorsimulator.entities.MessageProducer;
import sd.project.sensorsimulator.entities.Simulator;
import sd.project.sensorsimulator.services.WritingSensorService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Service
public class WritingSensorServiceImpl implements WritingSensorService {
    private final Simulator simulator;

    public WritingSensorServiceImpl(Simulator simulator) {
        this.simulator = simulator;
    }

    @Override
    public String addSensorId(String sensorId) throws IOException {
        ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) simulator.getApplicationContext()).getBeanFactory();
        MessageProducer messageProducer = new MessageProducer(simulator.getReadingDataService(), simulator.getRabbitTemplate(), simulator.getObjectMapper());
        messageProducer.setSensorId(sensorId);
        simulator.getMessageProducers().add(messageProducer);
        beanFactory.applyBeanPostProcessorsAfterInitialization(messageProducer, messageProducer.getClass().getCanonicalName());
        System.out.println("inainte de try");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/sensor_id.csv", true))) {
            System.out.println("intru in try");
            PrintWriter printWriter = new PrintWriter(bw);
            printWriter.println(sensorId);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return sensorId;
    }
}
