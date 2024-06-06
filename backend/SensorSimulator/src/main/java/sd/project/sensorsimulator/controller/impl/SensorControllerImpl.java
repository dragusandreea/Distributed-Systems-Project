package sd.project.sensorsimulator.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sd.project.sensorsimulator.controller.SensorController;
import sd.project.sensorsimulator.services.WritingSensorService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class SensorControllerImpl implements SensorController {
   private final WritingSensorService writingSensorService;

    public SensorControllerImpl(WritingSensorService writingSensorService) {
        this.writingSensorService = writingSensorService;
    }


    @Override
    public ResponseEntity<Map<String, Object>> add(String deviceId) throws IOException {
        writingSensorService.addSensorId(deviceId);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Added sensor with id = " + deviceId);
        return new ResponseEntity<>(body, HttpStatus.ACCEPTED);
    }
}
