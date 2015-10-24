package model;


import java.util.List;

public class ResultsSummary {

    private String carNumber;
    private List<Long> timesTaken;
    private long total;

    public void ResultsSummary() {

    }

    public ResultsSummary(String carNumber,  List<Long> timesTaken, long total){
        this.carNumber = carNumber;
        this.timesTaken = timesTaken;
        this.total = total;
    }

    public ResultsSummary() {
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public void setTimesTaken(List<Long>  timesTaken) {
        this.timesTaken = timesTaken;
    }


    public void setTotal(long total) {
        this.total = total;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public List<Long> getTimesTaken() {
        return timesTaken;
    }
    public long getTotal() {
        return total;
    }

}
