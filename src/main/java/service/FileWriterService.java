package service;

import model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
public class FileWriterService {
    private static Logger LOG = LoggerFactory.getLogger(FileWriterService.class);

    private static final String OUTPUT_FILENAME = "test.csv";

    public void appendStringToFile(Result result){
        try {
            Files.write(Paths.get(OUTPUT_FILENAME), result.toString().getBytes(), StandardOpenOption.CREATE,StandardOpenOption.APPEND);
        }catch (IOException e) {
            LOG.error(String.format("Something went wrong saving the results file: %s",OUTPUT_FILENAME),e);
        }
    }
}
