package timecard.service;

import org.springframework.stereotype.Service;

@Service
public class EventTypeService {

    public EventTypeService() {
    }

    public boolean dropsTimes(String eventType) {
        return true;
    }
}
