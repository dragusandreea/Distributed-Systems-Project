package sd.project.sensorsimulator.services;

import java.io.IOException;
import java.util.UUID;

public interface WritingSensorService {
    String addSensorId(String sensorId) throws IOException;
}
