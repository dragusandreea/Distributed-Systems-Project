package sd.project.devicemanagementservice.controllers.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sd.project.devicemanagementservice.controllers.DeviceController;
import sd.project.devicemanagementservice.dtos.DeviceDto;
import sd.project.devicemanagementservice.dtos.DeviceUpdateDto;
import sd.project.devicemanagementservice.facades.DeviceFacade;
import sd.project.devicemanagementservice.security.JwtService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class DeviceControllerImpl implements DeviceController {
    private final DeviceFacade deviceFacade;
    private final JwtService jwtService;

    public DeviceControllerImpl(DeviceFacade deviceFacade, JwtService jwtService) {
        this.deviceFacade = deviceFacade;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<DeviceDto> create(String token, DeviceDto deviceDto) throws JsonProcessingException {
        DeviceDto deviceDto1 = null;
        HttpStatus response = jwtService.authorizeByRole(token, null);
        if(response.equals(OK)) {
            deviceDto1 = deviceFacade.create(deviceDto);
        }
        return new ResponseEntity<>(deviceFacade.create(deviceDto1), response);
    }

    @Override
    public ResponseEntity<List<DeviceDto>> getAll(String token) {
        List<DeviceDto> deviceDtos = null;
        HttpStatus response = jwtService.authorizeByRole(token, null);
        if(response.equals(OK)) {
            deviceDtos = deviceFacade.getAll();
        }
        return new ResponseEntity<>(deviceDtos, response);
    }

    @Override
    public ResponseEntity<DeviceDto> getById(String token, UUID id) {
        DeviceDto deviceDto = null;
        HttpStatus response = jwtService.authorizeByRole(token, null);
        if(response.equals(OK)) {
            deviceDto = deviceFacade.getById(id);
        }
        return new ResponseEntity<>(deviceDto, response);
    }

    @Override
    public ResponseEntity<List<DeviceDto>> getByDescription(String token, String description) {
        List<DeviceDto> deviceDtos = null;
        HttpStatus response = jwtService.authorizeByRole(token, null);
        if(response.equals(OK)) {
            deviceDtos = deviceFacade.getByDescription(description);
        }
        return new ResponseEntity<>(deviceDtos, response);
    }

    @Override
    public ResponseEntity<List<DeviceDto>> getByAddress(String token, String address) {
        List<DeviceDto> deviceDtos = null;
        HttpStatus response = jwtService.authorizeByRole(token, null);
        if(response.equals(OK)) {
            deviceDtos = deviceFacade.getByAddress(address);
        }
        return new ResponseEntity<>(deviceDtos, response);
    }

    @Override
    public ResponseEntity<List<DeviceDto>> getByHourlyLimit(String token, Integer hourlyLimit) {
        List<DeviceDto> deviceDtos = null;
        HttpStatus response = jwtService.authorizeByRole(token, null);
        if(response.equals(OK)) {
            deviceDtos = deviceFacade.getByHourlyLimit(hourlyLimit);
        }
        return new ResponseEntity<>(deviceDtos, response);
    }

    @Override
    public ResponseEntity<List<DeviceDto>> getByHourlyLimitLessThan(String token, Integer hourlyLimit) {
        List<DeviceDto> deviceDtos = null;
        HttpStatus response = jwtService.authorizeByRole(token, null);
        if(response.equals(OK)) {
            deviceDtos = deviceFacade.getByHourlyLimitLessThan(hourlyLimit);
        }
        return new ResponseEntity<>(deviceDtos, response);
    }

    @Override
    public ResponseEntity<List<DeviceDto>> getByHourlyLimitGreaterThan(String token, Integer hourlyLimit) {
        List<DeviceDto> deviceDtos = null;
        HttpStatus response = jwtService.authorizeByRole(token, null);
        if(response.equals(OK)) {
            deviceDtos = deviceFacade.getByHourlyLimitGreaterThan(hourlyLimit);
        }
        return new ResponseEntity<>(deviceDtos, response);
    }

    @Override
    public ResponseEntity<List<DeviceDto>> getByOwnerId(String token, UUID ownerId) {
        List<DeviceDto> deviceDtos = null;
        HttpStatus response = jwtService.authorizeByRole(token, ownerId);
        if(response.equals(OK)) {
            deviceDtos = deviceFacade.getByOwnerId(ownerId);
        }
        return new ResponseEntity<>(deviceDtos, response);
    }

    @Override
    public ResponseEntity<DeviceUpdateDto> update(String token, DeviceUpdateDto deviceUpdateDto) {
        DeviceUpdateDto deviceUpdateDto1 = null;
        HttpStatus response = jwtService.authorizeByRole(token, null);
        if(response.equals(OK)) {
            deviceUpdateDto1 = deviceFacade.update(deviceUpdateDto);
        }
        return new ResponseEntity<>(deviceUpdateDto1, response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> deleteById(String token, UUID id) {
        Map<String, Object> deleted = null;
        HttpStatus response = jwtService.authorizeByRole(token, null);
        if(response.equals(OK)) {
            deleted = deviceFacade.deleteById(id);
        }
        return new ResponseEntity<>(deleted, response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> deleteByOwnerId(String token, UUID ownerId) {
        Map<String, Object> deleted = null;
        HttpStatus response = jwtService.authorizeByRole(token, null);
        if(response.equals(OK)) {
            deleted = deviceFacade.deleteByOwnerId(ownerId);
        }
        return new ResponseEntity<>(deleted, response);
    }
}
