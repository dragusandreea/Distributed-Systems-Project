package sd.project.monitoringservice.facade;

import sd.project.monitoringservice.dtos.ConsumptionDto;

import java.util.List;
import java.util.UUID;

public interface ConsumptionFacade {
    List<ConsumptionDto> getAllByOwnerId(UUID ownerId);
}
