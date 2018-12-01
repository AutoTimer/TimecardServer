package timecard.controller;

import timecard.model.RawTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import timecard.service.FileService;

import java.util.List;

@RestController
@RequestMapping("/raw-time")
public class RawTimeController {
    @Autowired
    private FileService fileService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public void saveTime(@RequestBody RawTime result) {
        fileService.appendEntityToFile(result);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<RawTime> getTime() {
        return fileService.readEntitiesFromFile(RawTime.class);
    }
}
