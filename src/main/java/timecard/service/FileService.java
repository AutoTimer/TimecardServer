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
import java.util.stream.Collectors;

@Service
public class FileService {
    private static Logger LOG = LoggerFactory.getLogger(FileService.class);

    public FileService() {
    }

    public <T> List<T> readEntitiesFromFile(Class<T> clazz) {
        List<T> result = new ArrayList<>();
        String filename = null;
        try {
            filename = String.format("%s%s.csv", getDateString(), clazz.getSimpleName().toLowerCase());
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (String line : lines) {
                result.add(clazz.getConstructor(String.class).newInstance(line));
            }
        } catch (IOException | NoSuchMethodException | InstantiationException | IllegalArgumentException |
                IllegalAccessException | InvocationTargetException e) {
            LOG.warn(String.format("Something went wrong reading the file: %s", filename), e);
        }
        return result;
    }

    private String getDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_");
        return sdf.format(new Date());
    }

    public void appendEntityToFile(Object object) {
        Path path = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_");
            path = Paths.get(String.format("%s%s.csv", sdf.format(new Date()), object.getClass().getSimpleName().toLowerCase()));
            Files.write(path, (object.toString() + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            LOG.error(String.format("Something went wrong appending to the file: %s", path.getFileName()), e);
        }
    }

    <T> boolean deleteFile(Class<T> clazz) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_");
        Path path = null;
        try {
            path = Paths.get(String.format("%s%s.csv", sdf.format(new Date()), clazz.getSimpleName().toLowerCase()));
            Files.delete(path);
        } catch (IOException e) {
            LOG.info(String.format("Something went wrong deleting the file: %s", path.getFileName()), e);
            return false;
        }
        return true;
    }

    public boolean deleteEntry(Object object){
        List existingList = readEntitiesFromFile(object.getClass());
        List newList = (List) existingList
                .stream()
                .map(Object::toString)
                .filter(s -> !object.toString().equals(s))
                .collect(Collectors.toList());
        deleteFile(object.getClass());
        newList.stream().map(s -> {
            try {
                return object.getClass().getConstructor(String.class).newInstance(s);
            } catch (InstantiationException |
                    IllegalAccessException |
                    InvocationTargetException |
                    NoSuchMethodException e) {
                LOG.error("something went bang",e);
                return false;
            }
        }).forEach(this::appendEntityToFile);
        return newList.size() == existingList.size() - 1;
    }
}