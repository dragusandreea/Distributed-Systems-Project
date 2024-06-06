package sd.project.sensorsimulator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CronConfiguration {
    @Value("${cron.reading.sensor.data}")
    private String cronValue;

    @Bean
    public String cronReadingSensorData() {
        return this.cronValue;
    }
}
