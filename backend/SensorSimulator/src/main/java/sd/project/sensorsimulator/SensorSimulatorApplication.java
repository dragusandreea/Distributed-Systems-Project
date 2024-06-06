package sd.project.sensorsimulator;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import sd.project.sensorsimulator.entities.Simulator;

@SpringBootApplication
@EnableScheduling
public class SensorSimulatorApplication {
	public static void main(String[] args)  {
		SpringApplication.run(SensorSimulatorApplication.class, args);
	}
	@Bean
	CommandLineRunner init(Simulator simulator) {
		return args -> {
			simulator.messageProducersListInitialization();
		};
	}
}
