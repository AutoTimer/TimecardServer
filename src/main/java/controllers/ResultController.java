package controllers;

import model.Result;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@EnableAutoConfiguration
@RequestMapping("/result")
public class ResultController {
    public static List<Result> results = new CopyOnWriteArrayList<>();

    @RequestMapping(method= RequestMethod.POST, consumes="application/json")
    public void saveTime(@RequestBody Result result) {
        results.add(result);
        FileWriterService fileWriterService = new FileWriterService();
        fileWriterService.appendResultToFile(result);
    }

  @RequestMapping(method= RequestMethod.GET)
    public List<Result> getTime() {
      return results;
  }


}
