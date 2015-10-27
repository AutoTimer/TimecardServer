package controllers;

import model.RawTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.springframework.util.StringUtils.trimTrailingWhitespace;

@RestController
@EnableAutoConfiguration
@RequestMapping("/result")
public class ResultController {
    private List<RawTime> results = new CopyOnWriteArrayList<>();
    @Autowired
    private FileWriterService fileWriterService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public void saveTime(@RequestBody RawTime result) {
        results.add(result);
        FileWriterService fileWriterService = new FileWriterService();
        fileWriterService.appendResultToFile(result);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<RawTime> getTime(@RequestParam(value = "carNumber", required = false) String carNumber) {
        List<RawTime> driverResult = new CopyOnWriteArrayList<>();
        results = fileWriterService.readFromFile();
        if (carNumber != null ){
            RawTime resultForADriver = getResultForADriver(results, carNumber);
            if(resultForADriver != null){
                driverResult.add(resultForADriver);
            }
            return driverResult;
        }
        return results;
    }

    private RawTime getResultForADriver(List<RawTime> results, String carNumber) {
        for (RawTime res : results) {
            if (trimTrailingWhitespace(res.getCarNumber()).equals(trimTrailingWhitespace(carNumber))) {
                return res;
            }
        }
        return null;
    }

}
