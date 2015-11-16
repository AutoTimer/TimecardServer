package timecard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import timecard.controller.FileService;
import timecard.model.Driver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DriverService {

    private Map<String,String> drivers;
    private FileService fileService;

    @Autowired
    public DriverService(FileService fileService) {
        this.drivers = new HashMap<>();
        this.fileService = fileService;
    }

    public String getClassName(String carNumber) {
        List<Driver> drivers = fileService.readDriversFromFile();
        Driver result = drivers.stream().filter(driver -> driver.getCarNumber().equals(carNumber)).findFirst().orElse(new Driver());
        return result.getClassName();
    }
}
