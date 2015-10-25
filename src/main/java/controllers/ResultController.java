package controllers;

import model.Result;
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
    private List<Result> results = new CopyOnWriteArrayList<>();
    @Autowired
    private FileWriterService fileWriterService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public void saveTime(@RequestBody Result result) {
        results.add(result);
        FileWriterService fileWriterService = new FileWriterService();
        fileWriterService.appendResultToFile(result);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Result> getTime(@RequestParam(value = "carNumber", required = false) String carNumber) {
        List<Result> driverResult = new CopyOnWriteArrayList<>();
        results = fileWriterService.readFromFile();
        if (carNumber != null ){
            Result resultForADriver = getResultForADriver(results, carNumber);
            if(resultForADriver != null){
                driverResult.add(resultForADriver);
            }
            return driverResult;
        }
        return results;
    }

    private Result getResultForADriver(List<Result> results, String carNumber) {
        for (Result res : results) {
            if (trimTrailingWhitespace(res.getCarNumber()).equals(trimTrailingWhitespace(carNumber))) {
                return res;
            }
        }
        return null;
    }

}
