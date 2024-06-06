package sd.project.devicemanagementservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.project.devicemanagementservice.dtos.DeviceDto;
import sd.project.devicemanagementservice.dtos.DeviceUpdateDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/devices")
public interface DeviceController {
    @PostMapping("/create")
    ResponseEntity<DeviceDto> create(@RequestHeader("Authorization") String token, @Valid @RequestBody DeviceDto deviceDto) throws JsonProcessingException;

    @GetMapping("/read/getAll")
    ResponseEntity<List<DeviceDto>> getAll(@RequestHeader("Authorization") String token);

    @GetMapping("/read/getById/{id}")
    ResponseEntity<DeviceDto> getById(@RequestHeader("Authorization") String token, @PathVariable UUID id);

    @GetMapping("/read/getByDescription/{description}")
    ResponseEntity<List<DeviceDto>> getByDescription(@RequestHeader("Authorization") String token, @PathVariable String description);

    @GetMapping("/read/getByAddress/{address}")
    ResponseEntity<List<DeviceDto>> getByAddress(@RequestHeader("Authorization") String token, @PathVariable String address);

    @GetMapping("/read/getByHourlyLimit/{hourlyLimit}")
    ResponseEntity<List<DeviceDto>> getByHourlyLimit(@RequestHeader("Authorization") String token, @PathVariable Integer hourlyLimit);

    @GetMapping("/read/getByHourlyLimitLessThan/{hourlyLimit}")
    ResponseEntity<List<DeviceDto>> getByHourlyLimitLessThan(@RequestHeader("Authorization") String token, @PathVariable Integer hourlyLimit);

    @GetMapping("/read/getByHourlyLimitGreaterThan/{hourlyLimit}")
    ResponseEntity<List<DeviceDto>> getByHourlyLimitGreaterThan(@RequestHeader("Authorization") String token, @PathVariable Integer hourlyLimit);

    @GetMapping("/read/getByOwnerId/{ownerId}")
    ResponseEntity<List<DeviceDto>> getByOwnerId(@RequestHeader("Authorization") String token, @PathVariable UUID ownerId);

    @PutMapping("/update")
    ResponseEntity<DeviceUpdateDto> update(@RequestHeader("Authorization") String token, @RequestBody DeviceUpdateDto deviceUpdateDto);

    @DeleteMapping("/delete/deleteById/{id}")
    ResponseEntity<Map<String, Object>> deleteById(@RequestHeader("Authorization") String token, @PathVariable UUID id);
    @DeleteMapping("/delete/deleteByOwnerId/{ownerId}")
    ResponseEntity<Map<String, Object>> deleteByOwnerId(@RequestHeader("Authorization") String token, @PathVariable UUID ownerId);
}
