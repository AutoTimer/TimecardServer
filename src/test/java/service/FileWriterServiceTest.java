package service;

import model.Result;
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
        Result result = new Result("car number", 1, 1, 1, "1234", false, 1);
        fileWriterService.appendResultToFile(result);
        //if we get here everthing is ok....shonky
    }
}