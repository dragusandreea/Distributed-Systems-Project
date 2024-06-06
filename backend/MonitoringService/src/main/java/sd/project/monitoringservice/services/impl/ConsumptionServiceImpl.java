package sd.project.monitoringservice.services.impl;

import org.springframework.stereotype.Service;
import sd.project.monitoringservice.entities.Consumption;
import sd.project.monitoringservice.repositories.ConsumptionRepository;
import sd.project.monitoringservice.services.ConsumptionService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConsumptionServiceImpl implements ConsumptionService {
    private final ConsumptionRepository consumptionRepository;

    public ConsumptionServiceImpl(ConsumptionRepository consumptionRepository) {
        this.consumptionRepository = consumptionRepository;
    }

    @Override
    public Consumption add(Consumption consumption) {
        return consumptionRepository.save(consumption);
    }

    @Override
    public List<Consumption> getAllByOwnerId(UUID ownerId) {
        return consumptionRepository.findAllByDevice_OwnerId(ownerId);
    }

    @Override
    public List<Consumption> getAllByDeviceIdAndTimestampIsAfterAndTimestampIsBefore(UUID deviceId, Timestamp timestampAfter, Timestamp timestampBefore) {
        return consumptionRepository.findAllByDevice_IdAndTimestampIsAfterAndTimestampIsBefore(deviceId, timestampAfter, timestampBefore);
    }

    @Override
    public Consumption update(Consumption consumption) {
        Optional<Consumption> consumptionOptional = consumptionRepository.findById(consumption.getId());
        if(consumptionOptional.isPresent()) {
            return consumptionRepository.save(consumption);
        } else {
            return null;
        }
    }
}
