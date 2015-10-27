package controllers;

import model.Event;
import model.RawTime;
import model.ResultsSummary;
import model.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static java.lang.Long.signum;

@RestController
@EnableAutoConfiguration
@RequestMapping("/results-summary")
public class ResultsSummaryController {
    private FileWriterService fileWriterService;

    @Autowired
    public ResultsSummaryController(FileWriterService fileWriterService) {
        this.fileWriterService = fileWriterService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Event getTime() {
        return calculateResultSummary();
    }

    public Event calculateResultSummary() {
        Event event = new Event();
        List<RawTime> rawTimes = fileWriterService.readFromFile();
        List<ResultsSummary> resultsSummary = new ArrayList<ResultsSummary>();
        List<Time> times = new ArrayList<>();

        for(RawTime rawTime:rawTimes){
            Time time = new Time(rawTime);
            times.add(time);
        }

        times.sort((t1, t2)->signum(t1.getElapsedTimeWithPenalties() - t2.getElapsedTimeWithPenalties()));

        event.addAll(times);

        return event;
    }
}
