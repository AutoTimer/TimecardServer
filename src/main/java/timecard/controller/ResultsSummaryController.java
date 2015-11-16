package timecard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import timecard.model.*;
import timecard.service.DriverService;
import timecard.service.FileService;

import java.util.*;

@RestController
@RequestMapping("/results-summary")
public class ResultsSummaryController {
    private FileService fileService;
    private DriverService driverService;

    @Autowired
    public ResultsSummaryController(FileService fileService, DriverService driverService) {
        this.fileService = fileService;
        this.driverService = driverService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public EventResponse getTimes() {
        Event event = new Event();
        List<RawTime> rawTimes = fileService.readResultsFromFile();
        List<Time> times = new ArrayList<>();

        for (RawTime rawTime : rawTimes) {
            Time time = new Time(rawTime, driverService.getClassName(rawTime.getCarNumber()));
            times.add(time);
        }

        event.addAll(times);

        List<LayoutResponse> layouts = getLayouts(event.getResultSummaries().values());

        padMissingLayouts(event, layouts);

        padMissingTimes(event, layouts);

        calculateTotals(event);

        return new EventResponse(event, layouts);
    }

    private void padMissingTimes(Event event, List<LayoutResponse> layouts) {
        event.getResultSummaries().forEach((carNumber, resultsSummary) -> {
            for (Map.Entry<String, List<Time>> entry : resultsSummary.getLayouts().entrySet()) {
                int runsInThisLayout = entry.getValue().size();
                int noOfRunsToPad = getMaxRunsInThisLayout(entry.getKey(), layouts) - runsInThisLayout;
                List<Time> nonZeroTimes = entry.getValue();
                for (int i = 0; i < noOfRunsToPad; i++) {
                    nonZeroTimes.add(new Time(carNumber, entry.getKey()));
                }
            }
        });
    }

    private void calculateTotals(Event event) {
        event.getResultSummaries().forEach((carNumber, resultsSummary) -> {
            long totalTimeForCar = 0;
            for (Map.Entry<String, List<Time>> layoutTimes : resultsSummary.getLayouts().entrySet()) {
                totalTimeForCar += layoutTimes.getValue().stream().mapToLong(Time::getElapsedTimeWithPenalties).sum();
            }
            resultsSummary.setTotal(totalTimeForCar);
        });
    }

    private int getMaxRunsInThisLayout(String layoutName, List<LayoutResponse> layouts) {
        int maxRunsInThisLayout = 0;
        for (LayoutResponse layoutResponse : layouts) {
            if (layoutName.equals(layoutResponse.getName())) {
                maxRunsInThisLayout = layoutResponse.getNoOfRuns();
            }
        }
        return maxRunsInThisLayout;
    }

    private void padMissingLayouts(Event event, List<LayoutResponse> layouts) {
        for (ResultsSummary resultsSummary : event.getResultSummaries().values()) {
            for (LayoutResponse layout : layouts) {
                Map<String, List<Time>> thisCarsLayouts = resultsSummary.getLayouts();
                thisCarsLayouts.putIfAbsent(layout.getName(), new ArrayList<Time>());
            }
        }
    }

    private List<LayoutResponse> getLayouts(Collection<ResultsSummary> resultSummaries) {
        Map<String, Integer> maxRunsPerLayout = new TreeMap<>();
        for (ResultsSummary resultsSummary : resultSummaries) {
            for (Map.Entry<String, List<Time>> layout : resultsSummary.getLayouts().entrySet()) {
                maxRunsPerLayout.putIfAbsent(layout.getKey(), 0);
                int runsInThisLayout = layout.getValue().size();
                if (maxRunsPerLayout.get(layout.getKey()) < runsInThisLayout) {
                    maxRunsPerLayout.put(layout.getKey(), runsInThisLayout);
                }
            }
        }

        List<LayoutResponse> layouts = new ArrayList<>();
        maxRunsPerLayout.forEach((layoutName, maxNoOfRuns) -> layouts.add(new LayoutResponse(layoutName, maxNoOfRuns)));
        return layouts;
    }
}
