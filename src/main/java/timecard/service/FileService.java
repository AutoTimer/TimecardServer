package timecard.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileService {
    private static Logger LOG = LoggerFactory.getLogger(FileService.class);

    public FileService() {
    }

    synchronized public <T> List<T> readEntitiesFromFile(Class<T> clazz) {
        List<T> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_");
        String filename = null;
        try {
            filename = String.format("%s%s.csv", sdf.format(new Date()), clazz.getSimpleName().toLowerCase());
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (String line : lines) {
                result.add(clazz.getConstructor(String.class).newInstance(line));
            }
        } catch (IOException | NoSuchMethodException | InstantiationException | IllegalArgumentException |
                IllegalAccessException | InvocationTargetException e) {
            LOG.error(String.format("Something went wrong reading the file: %s", filename), e);
        }
        return result;
    }

    synchronized public void appendEntityToFile(Object object) {
        Path path = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_");
            path = Paths.get(String.format("%s%s.csv", sdf.format(new Date()), object.getClass().getSimpleName().toLowerCase()));
            Files.write(path, (object.toString() + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            LOG.error(String.format("Something went wrong appending to the file file: %s", path.getFileName()), e);
        }
    }
}