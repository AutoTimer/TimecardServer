package timecard.service;

import timecard.model.Driver;
import timecard.model.RawTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

    //TODO factor out common file write method
    synchronized public void appendDriverToFile(Driver driver) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_");
            Files.write(Paths.get(String.format("%s%s", sdf.format(new Date()), DRIVERS_FILENAME)), (driver.toString() + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            LOG.error(String.format("Something went wrong saving the drivers file: %s", DRIVERS_FILENAME), e);
        }
    }

    synchronized public List<RawTime> readResultsFromFile() {
        return readEntitiesFromFile(RawTime.class,RESULTS_FILENAME);
    }

    //TODO extract this to a common file reading method
    synchronized public List<Driver> readDriversFromFile() {
        return readEntitiesFromFile(Driver.class,DRIVERS_FILENAME);
    }

    private <T> List<T> readEntitiesFromFile(Class<T> clazz, String filename) {
        List<T> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_");
        try {
            List<String> lines = Files.readAllLines(Paths.get(String.format("%s%s", sdf.format(new Date()), filename)));
            for (String line : lines) {
                result.add(clazz.getConstructor(String.class).newInstance(line));
            }
        } catch (IOException | NoSuchMethodException | InstantiationException | IllegalArgumentException |
                IllegalAccessException | InvocationTargetException e) {
            LOG.error(String.format("Something went wrong reading the file: %s%s", sdf.format(new Date()), filename), e);
        }
        return result;
    }
}
