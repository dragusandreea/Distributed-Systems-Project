package sd.project.sensorsimulator.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import sd.project.sensorsimulator.entities.MyBufferedReader;
import sd.project.sensorsimulator.services.ReadingDataService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class ReadingDataServiceImpl implements ReadingDataService {
    private final MyBufferedReader bufferedReaderData;
    private final MyBufferedReader bufferedReaderId;

    @Autowired
    public ReadingDataServiceImpl(@Qualifier("bufferedReaderSensorData") MyBufferedReader bufferedReaderData, @Qualifier("bufferedReaderSensorId") MyBufferedReader bufferedReaderId) {
        this.bufferedReaderData = bufferedReaderData;
        this.bufferedReaderId = bufferedReaderId;
    }

    @Override
    public String readSensorData() throws IOException {
        String line =  bufferedReaderData.readLine();
        if(line != null) {
            return line;
        } else {
            bufferedReaderData.mark(0);
            bufferedReaderData.reset();
            return bufferedReaderData.readLine();
        }
    }

    @Override
    public List<String> readSensorsIds() throws IOException {
        List<String> deviceIds = new ArrayList<>();
        String line;
        while((line = bufferedReaderId.readLine()) != null) {
            deviceIds.add(line);
        }
        return deviceIds;
    }


}
