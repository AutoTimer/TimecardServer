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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class FileServiceTest {

    @Before
    public void setup(){
        FileService fileService = new FileService();
        fileService.deleteFile(TestModel.class);
    }

    @After
    public void tearDown(){
        FileService fileService = new FileService();
        fileService.deleteFile(TestModel.class);
    }

    @Test
    public void concurrency() throws InterruptedException {
        FileService fileService = new FileService();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Callable<Object> appendToFile1 = () -> {
            IntStream.range(0, 500).forEach(i -> fileService.appendEntityToFile(new TestModel(Integer.toString(i))));
            return null;
        };
        Callable<Object> appendToFile2 = () -> {
            IntStream.range(500, 1000).forEach(i -> fileService.appendEntityToFile(new TestModel(Integer.toString(i))));
            return null;
        };

        List<Callable<Object>> callables = Arrays.asList(
                appendToFile1,
                appendToFile2);

        executor.invokeAll(callables);

        List<String> strings = fileService
                .readEntitiesFromFile(TestModel.class)
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        String[] expectedFileContents = new String[1000];
        IntStream.range(0,1000).forEach(i->expectedFileContents[i] = Integer.toString(i));

        assertThat(strings).containsOnly(expectedFileContents);
    }

    @Test
    public void deleteFileHappyPath(){
        FileService fileService = new FileService();
        fileService.appendEntityToFile(new TestModel("1"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_");
        Path path = Paths.get(String.format("%s%s.csv", sdf.format(new Date()), "testmodel"));

        assertThat(fileService.deleteFile(TestModel.class)).isTrue();

        assertThat(Files.exists(path)).isFalse();
    }

    @Test
    public void deleteFileReturnsFalseWhenFileDoesntExist(){
        FileService fileService = new FileService();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_");
        Path path = Paths.get(String.format("%s%s.csv", sdf.format(new Date()), "testmodel"));

        assertThat(fileService.deleteFile(TestModel.class)).isFalse();

        assertThat(Files.exists(path)).isFalse();
    }

    @Test
    public void deleteEntrySuccess(){
        FileService fileService = new FileService();
        fileService.appendEntityToFile(new TestModel("1"));

        assertThat(fileService.deleteEntry(new TestModel("1"))).isTrue();
    }

    @Test
    public void deleteEntryFail(){
        FileService fileService = new FileService();
        fileService.appendEntityToFile(new TestModel("1"));

        assertThat(fileService.deleteEntry(new TestModel("2"))).isFalse();
    }
}