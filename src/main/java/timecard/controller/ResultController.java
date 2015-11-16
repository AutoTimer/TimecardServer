package timecard.controller;

import timecard.model.RawTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import timecard.service.FileService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/result")
public class ResultController {
    @Autowired
    private FileService fileService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public void saveTime(@RequestBody RawTime result) {
        fileService.appendResultToFile(result);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<RawTime> getTime() {
        List<RawTime> driverResult = new CopyOnWriteArrayList<>();
        return fileService.readResultsFromFile();
    }
}
