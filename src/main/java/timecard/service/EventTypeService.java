package timecard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import timecard.model.Event;
import timecard.model.EventType;

import java.util.List;
import java.util.Optional;

@Service
public class EventTypeService {

    private final FileService fileService;

    @Autowired
    public EventTypeService(FileService fileService) {
        this.fileService = fileService;
    }

    public boolean dropsTimes(String name) {
        List<EventType> eventTypes = fileService.readEntitiesFromFile(EventType.class);
        Optional<EventType> optional = eventTypes.stream().filter(x -> x.getName().equals(name)).findFirst();
        if(optional.isPresent()){
            return optional.get().isDropsTimes();
        } else {
            return false;
        }
    }
}
