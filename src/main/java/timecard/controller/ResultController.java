package timecard.controller;

import timecard.model.RawTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import timecard.service.FileService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/result")
public class ResultController {
    @Autowired
    private FileService timesService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public void saveTime(@RequestBody RawTime result) {
        timesService.appendEntityToFile(result);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<RawTime> getTime() {
        List<RawTime> driverResult = new CopyOnWriteArrayList<>();
        return timesService.readEntitiesFromFile(RawTime.class);
    }
}
