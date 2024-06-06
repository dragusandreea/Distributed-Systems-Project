package sd.project.monitoringservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CronConfiguration {
    @Value("${cron.computing.hourly.limit}")
    private String cronValue;

    @Bean
    public String cronComputingHourlyLimit() {
        return this.cronValue;
    }
}
