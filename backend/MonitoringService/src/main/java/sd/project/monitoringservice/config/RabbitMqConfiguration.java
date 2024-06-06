package sd.project.monitoringservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {
    @Value("${rabbitmq.queue.name}")
    private String queueNameSensor;
    @Value("${rabbitmq.topic.exchange.name}")
    private String topicExchangeNameSensor;
    @Value("${rabbitmq.routing.key}")
    private String routingKeySensor;
    @Value("${rabbitmq.sync.queue.name}")
    private String queueNameSync;
    @Value("${rabbitmq.sync.topic.exchange.name}")
    private String topicExchangeNameSync;
    @Value("${rabbitmq.sync.routing.key}")
    private String routingKeySync;

    @Bean
    public Queue queue() {
        return new Queue(queueNameSensor);
    }

    @Bean
    public Queue queueSync() {
        return new Queue(queueNameSync);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(topicExchangeNameSensor);
    }
    @Bean
    public TopicExchange topicExchangeSync() {
        return new TopicExchange(topicExchangeNameSync);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with(routingKeySensor);
    }
    @Bean
    public Binding bindingSync() {
        return BindingBuilder.bind(queueSync()).to(topicExchange()).with(routingKeySync);
    }
}
