package timecard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import timecard.model.DriverResults;
import timecard.model.Results;
import timecard.model.Time;

import java.util.List;
import java.util.Map;

@Service
public class ResultsService {
    static final long WRONG_TEST_PENALTY = 20000;
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
        results.getResultsByCompetitor().forEach((carNumber, driverResults) -> {
            long totalTimeForCar = 0;
            for (Map.Entry<String, List<Time>> layoutTimesMap : driverResults.getLayouts().entrySet()) {
                List<Time> times = layoutTimesMap.getValue();
                for (int runNo = 0; runNo < times.size(); runNo++) {
                    Time time = times.get(runNo);
                    long wrongTestTime = getFastestTimeInClass(results, carNumber, layoutTimesMap.getKey(), runNo) + WRONG_TEST_PENALTY;
                    if (time.isWrongTest() || time.getElapsedTimeWithPenalties() > wrongTestTime) {
                        time.setElapsedTimeWithPenalties(wrongTestTime);
                    }
                    totalTimeForCar += time.getElapsedTimeWithPenalties();
                }
            }
            driverResults.setTotal(totalTimeForCar);
        });
    }


    public void dropTimes(Results results) {
        results.getResultsByCompetitor().forEach((carNumber, resultsSummary) -> {
            String eventType = driverService.getDriver(carNumber).getEventType();
            if (eventTypeService.dropsTimes(eventType)) {
                for (Map.Entry<String, List<Time>> layout : resultsSummary.getLayouts().entrySet()) {
                    Time slowestTimeOnLayout = new Time();
                    for (Time time : layout.getValue()) {
                        if (time.getElapsedTimeWithPenalties() > slowestTimeOnLayout.getElapsedTimeWithPenalties()) {
                            slowestTimeOnLayout = time;
                        }
                    }
                    slowestTimeOnLayout.setDropped(true);
                    resultsSummary.setTotal(resultsSummary.getTotal() - slowestTimeOnLayout.getElapsedTimeWithPenalties());
                }
            }
        });
    }

    public long getFastestTimeInClass(Results results, String carNumber, String layout, int runIndex) {
        long fastestTime = Long.MAX_VALUE;
        String className = driverService.getDriver(carNumber).getClassName();
        for (DriverResults driverResults : results.getResultsByCompetitor().values()) {
            if (className.equals(driverService.getDriver(driverResults.getCarNumber()).getClassName())) {
                Time time = driverResults.getLayouts().get(layout).get(runIndex);
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