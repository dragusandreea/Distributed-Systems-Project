package sd.project.monitoringservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Device {
    @Id
    private UUID id;
    private String description;
    private String address;
    private Integer hourlyEnergyConsumptionLimit;
    private UUID ownerId;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "device")
    @ToString.Exclude
    private List<SensorMessage> sensorMessageList;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "device")
    @ToString.Exclude
    private List<Consumption> consumptionList;
}
