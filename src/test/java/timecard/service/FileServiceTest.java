package timecard.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class FileServiceTest {

    @Before
    public void setup(){
        FileService fileService = new FileService();
        fileService.deleteFile(String.class);
    }

    @After
    public void tearDown(){
        FileService fileService = new FileService();
        fileService.deleteFile(String.class);
    }

    @Test
    public void concurrency() throws InterruptedException {
        FileService fileService = new FileService();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Callable<Object> appendToFile = () -> {
            IntStream.range(0, 500).forEach(i -> fileService.appendEntityToFile("supercalifragilisticexpealidocious"));
            return null;
        };

        List<Callable<Object>> callables = Arrays.asList(
                appendToFile,
                appendToFile);

        executor.invokeAll(callables);

        List<String> strings = fileService.readEntitiesFromFile(String.class);

        String[] expectedFileContents = new String[1000];
        IntStream.range(0,1000).forEach(i->expectedFileContents[i] = "supercalifragilisticexpealidocious");

        assertThat(strings).containsExactly(expectedFileContents);
    }

    @Test
    public void deleteFileHappyPath(){
        FileService fileService = new FileService();
        fileService.appendEntityToFile("some random string");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_");
        Path path = Paths.get(String.format("%s%s.csv", sdf.format(new Date()), "string"));

        assertThat(fileService.deleteFile(String.class)).isTrue();

        assertThat(Files.exists(path)).isFalse();
    }

    @Test
    public void deleteFileReturnsFalseWhenFileDoesntExist(){
        FileService fileService = new FileService();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_");
        Path path = Paths.get(String.format("%s%s.csv", sdf.format(new Date()), "string"));

        assertThat(fileService.deleteFile(String.class)).isFalse();

        assertThat(Files.exists(path)).isFalse();
    }
}