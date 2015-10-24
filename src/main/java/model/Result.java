package model;


public class Result {

    private String driverName;
    private String carNumber;
    private String layout;

    private long startTime;
    private long endTime;
    private String runTime;

    public Result() {
    }

    public Result(String driverName, String carNumber, String layout, long startTime, long endTime, String runTime) {
        this.driverName = driverName;
        this.carNumber = carNumber;
        this.layout = layout;
        this.startTime = startTime;
        this.runTime = runTime;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public String getLayout() {
        return layout;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }
    
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s", driverName, carNumber, layout, startTime, endTime, runTime);
    }
}
