package service;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alec on 24/10/15.
 */
public class FileWriterServiceTest {

    @Test
    public void writeToAFileHappyPath(){
        FileWriterService fileWriterService = new FileWriterService();
        fileWriterService.appendStringToFile("lala");
        //if we get here everthing is ok....shonky
    }
}