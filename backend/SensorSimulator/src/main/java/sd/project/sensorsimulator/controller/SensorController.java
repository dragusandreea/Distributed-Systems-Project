package sd.project.sensorsimulator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/sensors")
public interface SensorController {
    @PostMapping("/add/{deviceId}")
    ResponseEntity<Map<String, Object>> add(@PathVariable String deviceId) throws IOException;
}
