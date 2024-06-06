package sd.project.monitoringservice.facade.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import sd.project.monitoringservice.dtos.ConsumptionDto;
import sd.project.monitoringservice.facade.ConsumptionFacade;
import sd.project.monitoringservice.services.ConsumptionService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ConsumptionFacadeImpl implements ConsumptionFacade {
    private final ConsumptionService consumptionService;
    private final ModelMapper modelMapper;

    public ConsumptionFacadeImpl(ConsumptionService consumptionService, ModelMapper modelMapper) {
        this.consumptionService = consumptionService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ConsumptionDto> getAllByOwnerId(UUID ownerId) {
        return consumptionService.getAllByOwnerId(ownerId)
                .stream()
                .filter(consumption -> consumption.getDevice().getOwnerId().equals(ownerId))
                .map(consumption -> modelMapper.map(consumption, ConsumptionDto.class))
                .collect(Collectors.toList());
    }
}
