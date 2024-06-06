package sd.project.sensorsimulator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import sd.project.sensorsimulator.entities.MyBufferedReader;

import java.io.FileReader;
import java.io.IOException;

@Configuration
@EnableAsync
@EnableScheduling
public class ApplicationConfiguration  {
    @Value("${sensor.data.file}")
    private String dataFileName;
    @Value("${sensor.id.file}")
    private String idFileName;

    @Bean
    @Scope("singleton")
    public MyBufferedReader bufferedReaderSensorData() {
        try (MyBufferedReader br = new MyBufferedReader(new FileReader(dataFileName))) {
            return br;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Bean
    @Scope("singleton")
    public MyBufferedReader bufferedReaderSensorId() {
        try (MyBufferedReader br = new MyBufferedReader(new FileReader(idFileName))) {
            return br;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.setThreadNamePrefix("TaskScheduler");
        return threadPoolTaskScheduler;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
