package controllers;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static java.lang.Long.max;
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
        List<Time> times = new ArrayList<>();

        for(RawTime rawTime:rawTimes){
            Time time = new Time(rawTime);
            times.add(time);
        }

        times.sort((t1, t2)->signum(t1.getElapsedTimeWithPenalties() - t2.getElapsedTimeWithPenalties()));
        event.addAll(times);

        //pad here with zeros for missing runs
        Map<String,Integer> maxRunsPerLayout = new TreeMap<>();
        for(ResultsSummary resultsSummary:event.getResultSummaries().values()){
            for(Map.Entry<String,List<Time>> layout:resultsSummary.getLayouts().entrySet()){
                maxRunsPerLayout.putIfAbsent(layout.getKey(), 0);
                int runsInThisLayout = layout.getValue().size();
                if(maxRunsPerLayout.get(layout.getKey())<runsInThisLayout){
                    maxRunsPerLayout.put(layout.getKey(), runsInThisLayout);
                }
            }
        }

        //Pad missing layouts within resultsSummary
        //TODO
        for(ResultsSummary resultsSummary:event.getResultSummaries().values()){
            for(String layout:maxRunsPerLayout.keySet()){
                Map<String,List<Time>> thisCarsLayouts = resultsSummary.getLayouts();
                thisCarsLayouts.putIfAbsent(layout,new ArrayList<Time>());
            }
        }

        //Pad missing times within layout
        for(ResultsSummary resultsSummary:event.getResultSummaries().values()){
            long totalTime = 0;
            for(Map.Entry<String,List<Time>> layout :resultsSummary.getLayouts().entrySet()){
                int maxRunsInThisLayout = maxRunsPerLayout.get(layout.getKey());
                int runsInThisLayout = layout.getValue().size();
                int noOfRunsToPad = maxRunsInThisLayout - runsInThisLayout;
                List<Time> nonZeroTimes = layout.getValue();
                for(int i=0; i<noOfRunsToPad;i++){
                    //FIXME we're going to end up with null car numbers here, is that a bad thing?
                    nonZeroTimes.add(new Time(layout.getKey()));
                }
                totalTime += nonZeroTimes.stream().mapToLong(Time::getElapsedTimeWithPenalties).sum();
            }
            resultsSummary.setTotal(totalTime);
        }


        return event;
    }
}
