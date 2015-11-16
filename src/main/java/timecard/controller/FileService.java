package timecard.controller;

import timecard.model.Driver;
import timecard.model.RawTime;
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
public class FileService {
    private static Logger LOG = LoggerFactory.getLogger(FileService.class);

    private static final String RESULTS_FILENAME = "results.csv";
    private static final String DRIVERS_FILENAME = "drivers.csv";

    synchronized public void appendResultToFile(RawTime result) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_");
            Files.write(Paths.get(String.format("%s%s", sdf.format(new Date()), RESULTS_FILENAME)), (result.toString() + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            LOG.error(String.format("Something went wrong saving the results file: %s", RESULTS_FILENAME), e);
        }
    }

    synchronized public List<RawTime> readResultsFromFile(){
        List<RawTime> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_");
        try {
            List<String> lines = Files.readAllLines(Paths.get(String.format("%s%s", sdf.format(new Date()), RESULTS_FILENAME)));
            for(String line:lines){
                result.add(new RawTime(line));
            }
        } catch (IOException e) {
            LOG.error(String.format("Something went wrong reading the results file: %s", String.format("%s%s", sdf.format(new Date()), RESULTS_FILENAME)),e);
        }
        return result;
    }

    //TODO extract this to a common file reading method
    synchronized public List<Driver> readDriversFromFile() {
        List<Driver> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_");
        try {
            List<String> lines = Files.readAllLines(Paths.get(String.format("%s%s", sdf.format(new Date()), DRIVERS_FILENAME)));
            for(String line:lines){
                result.add(new Driver(line));
            }
        } catch (IOException e) {
            LOG.error(String.format("Something went wrong reading the drivers file: %s", String.format("%s%s", sdf.format(new Date()), DRIVERS_FILENAME)),e);
        }
        return result;
    }
}
