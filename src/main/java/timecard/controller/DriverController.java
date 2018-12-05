package timecard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import timecard.model.Driver;
import timecard.service.DriverService;

import java.util.List;

@RestController
@RequestMapping("/driver")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteDriver(@RequestBody Driver driver){
        driverService.deleteDriver(driver);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public void saveDriver(@RequestBody Driver driver) {
        driverService.appendEntityToFile(driver);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Driver> getDrivers() {
        return driverService.getDrivers();
    }
}
