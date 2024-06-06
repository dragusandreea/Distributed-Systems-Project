package sd.project.sensorsimulator.services;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ReadingDataService {
    String readSensorData() throws IOException;
    List<String> readSensorsIds() throws IOException;
}
