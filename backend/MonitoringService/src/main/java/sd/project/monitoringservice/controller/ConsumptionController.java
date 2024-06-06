package sd.project.monitoringservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.project.monitoringservice.dtos.ConsumptionDto;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/consumption")
public interface ConsumptionController {
    @GetMapping("/read/getAllByOwnerId/{ownerId}")
    ResponseEntity<List<ConsumptionDto>> getAllByOwnerId(@RequestHeader("Authorization") String token, @PathVariable UUID ownerId);
}
