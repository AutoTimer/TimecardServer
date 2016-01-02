package timecard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import timecard.model.*;
import timecard.service.DriverService;
import timecard.service.TimesService;

import java.util.*;

@RestController
@RequestMapping("/results-summary")
public class ResultsSummaryController {
    private static final long WRONG_TEST_PENALTY = 30000;
    private TimesService timesService;
    private DriverService driverService;

    @Autowired
    public ResultsSummaryController(TimesService timesService, DriverService driverService) {
        this.timesService = timesService;
        this.driverService = driverService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public EventResponse getTimes() {
        Event event = new Event();
        List<RawTime> rawTimes = timesService.readResultsFromFile();
        List<Time> times = new ArrayList<>();

        for (RawTime rawTime : rawTimes) {
            Time time = new Time(rawTime, driverService.getDriver(rawTime.getCarNumber()));
            times.add(time);
        }

        event.addAll(times);

        List<LayoutResponse> layouts = getLayouts(event.getResultSummaries().values());

        padMissingLayouts(event, layouts);

        padMissingTimes(event, layouts);

        calculateTotals(event);

        return buildEventResponse(event, layouts);
    }

    private EventResponse buildEventResponse(Event event, List<LayoutResponse> layouts) {
        List<ResultSummaryResponse> results = new ArrayList<>();
        for (ResultsSummary summary : event.getResultSummaries().values()) {
            List<Time> timesToReturn = new ArrayList<>();
            summary.getLayouts().values().forEach(timesToReturn::addAll);
            results.add(new ResultSummaryResponse(summary.getCarNumber(), driverService.getDriver(summary.getCarNumber()), timesToReturn, summary.getTotal()));
        }

        results.sort(ResultSummaryResponse::compareByEventTypeClassAndTime);
        return new EventResponse(layouts, results);
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
            for (Map.Entry<String, List<Time>> layoutTimesMap : resultsSummary.getLayouts().entrySet()) {
                List<Time> times = layoutTimesMap.getValue();
                for (int runNo = 0; runNo < times.size(); runNo++) {
                    Time time = times.get(runNo);
                    if (time.isWrongTest()) {
                        long wrongTestTime = getFastestTimeInClass(event, carNumber, layoutTimesMap.getKey(), runNo) + WRONG_TEST_PENALTY;
                        time.setElapsedTimeWithPenalties(wrongTestTime);
                    }
                    totalTimeForCar += time.getElapsedTimeWithPenalties();
                }
            }
            resultsSummary.setTotal(totalTimeForCar);
        });
    }

    private long getFastestTimeInClass(Event event, String carNumber, String layout, int runNo) {
        long fastestTime = Long.MAX_VALUE;
        String className = driverService.getDriver(carNumber).getClassName();
        for (ResultsSummary resultsSummary : event.getResultSummaries().values()) {
            if (carNumber.equals(resultsSummary.getCarNumber())) {
                continue;
            }
            if (className.equals(driverService.getDriver(resultsSummary.getCarNumber()).getClassName())) {
                Time time = resultsSummary.getLayouts().get(layout).get(runNo);
                if (!time.isWrongTest()) {
                    long thisTime = time.getElapsedTimeWithPenalties();
                    if (thisTime < fastestTime) {
                        fastestTime = thisTime;
                    }
                }
            }
        }
        return fastestTime == Long.MAX_VALUE ? 0 : fastestTime;
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
