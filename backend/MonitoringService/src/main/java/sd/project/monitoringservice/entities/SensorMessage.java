package sd.project.monitoringservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class SensorMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Timestamp timestamp;
    private Float value;
    @ManyToOne
    @JoinColumn(name = "deviceId")
    private Device device;
}
