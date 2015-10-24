package model;


public class Result {

    private String driverName;
    private String carNumber;
    private String layout;
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


}
