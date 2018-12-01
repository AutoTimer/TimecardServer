package timecard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import timecard.model.Driver;
import java.util.List;

@Service
public class DriverService {

    private FileService fileService;

    @Autowired
    public DriverService(FileService timesService) {
        this.fileService = timesService;
    }

    public Driver getDriver(String carNumber) {
        List<Driver> drivers = fileService.readEntitiesFromFile(Driver.class);
        Driver result = drivers.stream().filter(driver -> driver.getCarNumber().equals(carNumber)).findFirst().orElse(new Driver());
        return result;
    }
}
