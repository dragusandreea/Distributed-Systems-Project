package sd.project.monitoringservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Consumption {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Timestamp timestamp;
    private float hourlyConsumption;
    @ManyToOne
    @JoinColumn(name = "deviceId")
    private Device device;
}
