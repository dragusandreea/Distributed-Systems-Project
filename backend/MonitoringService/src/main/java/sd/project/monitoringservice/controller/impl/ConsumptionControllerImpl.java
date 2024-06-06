package sd.project.monitoringservice.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sd.project.monitoringservice.controller.ConsumptionController;
import sd.project.monitoringservice.dtos.ConsumptionDto;
import sd.project.monitoringservice.facade.ConsumptionFacade;
import sd.project.monitoringservice.security.JwtService;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class ConsumptionControllerImpl implements ConsumptionController {
    private final ConsumptionFacade consumptionFacade;
    private final JwtService jwtService;

    public ConsumptionControllerImpl(ConsumptionFacade consumptionFacade, JwtService jwtService) {
        this.consumptionFacade = consumptionFacade;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<List<ConsumptionDto>> getAllByOwnerId(String token, UUID ownerId) {
        List<ConsumptionDto> consumptionDtos = null;
        HttpStatus response = jwtService.authorizeByRole(token, ownerId);
        if(response.equals(OK)) {
            consumptionDtos = consumptionFacade.getAllByOwnerId(ownerId);
        }
        return new ResponseEntity<>(consumptionDtos, response);
    }
}
