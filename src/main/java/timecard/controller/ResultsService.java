package timecard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import timecard.model.Results;
import timecard.model.DriverResults;
import timecard.model.Time;
import timecard.service.DriverService;
import timecard.service.EventTypeService;

import java.util.*;

@Service
public class ResultsService {
    static final long WRONG_TEST_PENALTY = 30000;
    private DriverService driverService;
    private EventTypeService eventTypeService;

    @Autowired
    public ResultsService(DriverService driverService, EventTypeService eventTypeService) {
        this.driverService = driverService;
        this.eventTypeService = eventTypeService;
    }

    public Results calculateResults(Results results){
        calculateTotals(results);
        dropTimes(results);
        return results;
    }

    private void calculateTotals(Results results) {
        results.getResultByCompetitor().forEach((carNumber, driverResults) -> {
            long totalTimeForCar = 0;
            for (Map.Entry<String, List<Time>> layoutTimesMap : driverResults.getLayouts().entrySet()) {
                List<Time> times = layoutTimesMap.getValue();
                for (int runNo = 0; runNo < times.size(); runNo++) {
                    Time time = times.get(runNo);
                    if (time.isWrongTest()) {
                        long wrongTestTime = getFastestTimeInClass(results, carNumber, layoutTimesMap.getKey(), runNo) + WRONG_TEST_PENALTY;
                        time.setElapsedTimeWithPenalties(wrongTestTime);
                    }
                    totalTimeForCar += time.getElapsedTimeWithPenalties();
                }
            }
            driverResults.setTotal(totalTimeForCar);
        });
    }


    private void dropTimes(Results results) {
        results.getResultByCompetitor().forEach((carNumber, resultsSummary) -> {
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

    private long getFastestTimeInClass(Results results, String carNumber, String layout, int runNo) {
        long fastestTime = Long.MAX_VALUE;
        String className = driverService.getDriver(carNumber).getClassName();
        for (DriverResults driverResults : results.getResultByCompetitor().values()) {
            if (carNumber.equals(driverResults.getCarNumber())) {
                continue;
            }
            if (className.equals(driverService.getDriver(driverResults.getCarNumber()).getClassName())) {
                Time time = driverResults.getLayouts().get(layout).get(runNo);
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


}