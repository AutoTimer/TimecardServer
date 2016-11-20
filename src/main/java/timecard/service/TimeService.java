package timecard.service;

import org.springframework.beans.factory.annotation.Autowired;
import timecard.model.RawTime;
import timecard.model.Time;

import java.util.List;
import java.util.stream.Collectors;

public class TimeService {

    private FileService fileService;

    @Autowired
    public TimeService(FileService fileService) {
        this.fileService = fileService;
    }

    public List<Time> getTimes() {
        return fileService.readEntitiesFromFile(RawTime.class).stream().map(Time::new).collect(Collectors.toList());
    }
}
