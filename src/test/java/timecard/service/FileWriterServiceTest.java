package timecard.service;

import timecard.model.RawTime;
import org.junit.Test;

public class FileWriterServiceTest {

    @Test
    public void writeToAFileHappyPath(){
        FileService fileService = new FileService();
        long startTime = 12345L;
        long endTime =  12345678910L;
        RawTime result = new RawTime("car number2", "A", 1, 1, false, 1);
        fileService.appendResultToFile(result);
        //if we get here everthing is ok....shonky
    }
}