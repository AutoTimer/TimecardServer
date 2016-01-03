package timecard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import timecard.model.EventType;

import java.util.List;

@Service
public class EventTypeService {

    private final FileService fileService;

    @Autowired
    public EventTypeService(FileService fileService) {
        this.fileService = fileService;
    }

    public boolean dropsTimes(String name) {
        List<EventType> eventTypes = fileService.readEntitiesFromFile(EventType.class);
        EventType event = eventTypes.stream().filter(x -> x.getName().equals(name)).findFirst().get();
        return event.isDropsTimes();
    }
}
