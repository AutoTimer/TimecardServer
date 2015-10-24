package model;


public class Result {

    private String driverName;
    private String carNumber;
    private String layout;

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    private String timeTaken;
    private String runTime;

    public Result() {

    }

    public Result(String driverName, String carNumber, String layout, String timeTaken, String runTime) {

        this.driverName = driverName;
        this.carNumber = carNumber;
        this.layout = layout;
        this.timeTaken = timeTaken;
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

    public String getTimeTaken() {
        return timeTaken;
    }

    public String getRunTime() {
        return runTime;
    }
}
