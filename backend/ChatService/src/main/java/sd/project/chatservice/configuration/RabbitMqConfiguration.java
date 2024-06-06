package sd.project.chatservice.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {
    @Value("${rabbitmq.sync.queue.name}")
    private String queueNameSync;
    @Value("${rabbitmq.sync.topic.exchange.name}")
    private String topicExchangeNameSync;
    @Value("${rabbitmq.sync.routing.key}")
    private String routingKeySync;

    @Bean
    public Queue queueSync() {
        return new Queue(queueNameSync);
    }

    @Bean
    public TopicExchange topicExchangeSync() {
        return new TopicExchange(topicExchangeNameSync);
    }

    @Bean
    public Binding bindingSync() {
        return BindingBuilder.bind(queueSync()).to(topicExchangeSync()).with(routingKeySync);
    }
}
