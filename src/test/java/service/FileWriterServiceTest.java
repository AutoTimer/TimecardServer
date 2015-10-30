package service;

import controllers.FileWriterService;
import model.RawTime;
import org.junit.Test;

/**
 * Created by alec on 24/10/15.
 */
public class FileWriterServiceTest {

    @Test
    public void writeToAFileHappyPath(){
        FileWriterService fileWriterService = new FileWriterService();
        long startTime = 12345L;
        long endTime =  12345678910L;
        RawTime result = new RawTime("car number2", "A", 1, 1, false, 1);
        fileWriterService.appendResultToFile(result);
        //if we get here everthing is ok....shonky
    }
}