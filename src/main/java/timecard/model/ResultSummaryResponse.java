package timecard.model;

import java.util.List;

public class ResultSummaryResponse {

    private String carNumber;
    private String className;
    private List<LayoutResponse> layouts;
    private List<Time> times;
    private long totalTime;


    public ResultSummaryResponse(String carNumber, String className, List<Time> times, long totalTime) {
        this.carNumber = carNumber;
        this.className = className;
        this.times = times;
        this.totalTime = totalTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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
}
