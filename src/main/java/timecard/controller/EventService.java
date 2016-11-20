package timecard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import timecard.model.Event;
import timecard.model.Layout;
import timecard.model.ResultsSummary;
import timecard.model.Time;
import timecard.service.DriverService;
import timecard.service.EventTypeService;

import java.util.*;

@Service
public class EventService {
    static final long WRONG_TEST_PENALTY = 30000;
    private DriverService driverService;
    private EventTypeService eventTypeService;

    @Autowired
    public EventService(DriverService driverService, EventTypeService eventTypeService) {
        this.driverService = driverService;
        this.eventTypeService = eventTypeService;
    }

    public Event calculateResults(Event event){
        padMissingLayouts(event);

        padMissingTimes(event);

        calculateTotals(event);

        dropTimes(event);

        return event;
    }

    private void dropTimes(Event event) {
        event.getResultSummariesByCar().forEach((carNumber, resultsSummary) -> {
            String eventType = driverService.getDriver(carNumber).getEventType();
            if (eventTypeService.dropsTimes(eventType)) {
                for (Map.Entry<String, List<Time>> layout : resultsSummary.getLayouts().entrySet()) {
                    Time maxTimeOnLayout = new Time();
                    for (Time time : layout.getValue()) {
                        if (time.getElapsedTimeWithPenalties() > maxTimeOnLayout.getElapsedTimeWithPenalties()) {
                            maxTimeOnLayout = time;
                        }
                    }
                    maxTimeOnLayout.setDropped(true);
                    resultsSummary.setTotal(resultsSummary.getTotal() - maxTimeOnLayout.getElapsedTimeWithPenalties());
                }
            }
        });
    }

    private void padMissingTimes(Event event) {
        event.getResultSummariesByCar().forEach((carNumber, resultsSummary) -> {
            for (Map.Entry<String, List<Time>> entry : resultsSummary.getLayouts().entrySet()) {
                int runsInThisLayout = entry.getValue().size();
                int noOfRunsToPad = getMaxRunsInThisLayout(entry.getKey(), event.getLayouts()) - runsInThisLayout;
                List<Time> nonZeroTimes = entry.getValue();
                for (int i = 0; i < noOfRunsToPad; i++) {
                    nonZeroTimes.add(new Time(carNumber, entry.getKey()));
                }
            }
        });
    }

    private void calculateTotals(Event event) {
        event.getResultSummariesByCar().forEach((carNumber, resultsSummary) -> {
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
        for (ResultsSummary resultsSummary : event.getResultSummariesByCar().values()) {
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

    private int getMaxRunsInThisLayout(String layoutName, List<Layout> layouts) {
        int maxRunsInThisLayout = 0;
        for (timecard.model.Layout Layout : layouts) {
            if (layoutName.equals(Layout.getName())) {
                maxRunsInThisLayout = Layout.getNoOfRuns();
            }
        }
        return maxRunsInThisLayout;
    }

    private void padMissingLayouts(Event event) {
        for (ResultsSummary resultsSummary : event.getResultSummariesByCar().values()) {
            for (Layout layout : event.getLayouts()) {
                Map<String, List<Time>> thisCarsLayouts = resultsSummary.getLayouts();
                thisCarsLayouts.putIfAbsent(layout.getName(), new ArrayList<>());
            }
        }
    }
}