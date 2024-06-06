package sd.project.sensorsimulator.entities;

import java.io.BufferedReader;
import java.io.Reader;

public class MyBufferedReader extends BufferedReader {
    public MyBufferedReader(Reader in) {
        super(in);
    }

    @Override
    public void close() {

    }

}
