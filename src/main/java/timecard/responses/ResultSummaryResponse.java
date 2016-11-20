package timecard.responses;

import timecard.model.Driver;
import timecard.model.Layout;
import timecard.model.Time;

import java.util.List;

import static java.lang.Long.signum;

public class ResultSummaryResponse {

    private String carNumber;
    private Driver driver;
    private List<Layout> layouts;
    private List<Time> times;
    private long totalTime;


    public ResultSummaryResponse(String carNumber, Driver driver, List<Time> times, long totalTime) {
        this.carNumber = carNumber;
        this.driver = driver;
        this.times = times;
        this.totalTime = totalTime;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public static int compareByEventTypeClassAndTime(ResultSummaryResponse a, ResultSummaryResponse b) {
        if (a.getDriver().getEventType().equals(b.getDriver().getEventType())){
            if (a.getDriver().getClassName().equals(b.getDriver().getClassName())) {
                return signum(a.getTotalTime() - b.getTotalTime());
            }
            return a.getDriver().getClassName().compareTo(b.getDriver().getClassName());
        }
        return a.getDriver().getEventType().compareTo(b.getDriver().getEventType());
    }
}
