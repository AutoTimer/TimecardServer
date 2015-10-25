package controllers;

import model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileWriterService {
    private static Logger LOG = LoggerFactory.getLogger(FileWriterService.class);

    private static final String OUTPUT_FILENAME = "results.csv";

    public void appendResultToFile(Result result) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_");
            Files.write(Paths.get(String.format("%s%s", sdf.format(new Date()), OUTPUT_FILENAME)), (result.toString() + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            LOG.error(String.format("Something went wrong saving the results file: %s", OUTPUT_FILENAME), e);
        }
    }

    public List<Result> readFromFile(){
        List<Result> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_");
        try {
            List<String> lines = Files.readAllLines(Paths.get(String.format("%s%s", sdf.format(new Date()), OUTPUT_FILENAME)));
            for(String line:lines){
                result.add(new Result(line));
            }
        } catch (IOException e) {
            LOG.error(String.format("Something went wrong reading the results file: %s", String.format("%s%s", sdf.format(new Date()), OUTPUT_FILENAME)),e);
        }
        return result;
    }
}
