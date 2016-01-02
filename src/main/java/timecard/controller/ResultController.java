package timecard.controller;

import timecard.model.RawTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import timecard.service.TimesService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/result")
public class ResultController {
    @Autowired
    private TimesService timesService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public void saveTime(@RequestBody RawTime result) {
        timesService.appendResultToFile(result);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<RawTime> getTime() {
        List<RawTime> driverResult = new CopyOnWriteArrayList<>();
        return timesService.readResultsFromFile();
    }
}
