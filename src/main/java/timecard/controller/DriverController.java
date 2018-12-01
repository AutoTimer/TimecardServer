package timecard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import timecard.model.Driver;
import timecard.service.FileService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/driver")
public class DriverController {
    @Autowired
    private FileService driverService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public void saveDriver(@RequestBody Driver driver) {
        driverService.appendEntityToFile(driver);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Driver> getDrivers() {
        return driverService.readEntitiesFromFile(Driver.class);
    }
}
