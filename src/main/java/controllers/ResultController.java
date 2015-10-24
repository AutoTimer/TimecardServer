package controllers;

import model.Result;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.FileWriterService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@EnableAutoConfiguration
@RequestMapping("/result")
public class ResultController {
    private static List<Result> results = new CopyOnWriteArrayList<>();

    @RequestMapping(method= RequestMethod.POST, consumes="application/json")
    public void saveTime(@RequestBody Result result) {
        results.add(result);
        FileWriterService fileWriterService = new FileWriterService();
        fileWriterService.appendResultToFile(result);
    }

  @RequestMapping(method= RequestMethod.GET)
    public List<Result> getTime() {
      List<Result> modifiableResult = new ArrayList<>(results);

        Collections.sort(modifiableResult, new Comparator<Result>() {

            public int compare(Result r1, Result r2) {
                return r1.getEndTime() - r1.getStartTime() > r2.getEndTime() - r2.getStartTime() ? 1 : -1;
            }
        });

      return modifiableResult;
    }


    public static void main(String[] args) throws Exception {
        SpringApplication.run(ResultController.class, args);
    }

}
