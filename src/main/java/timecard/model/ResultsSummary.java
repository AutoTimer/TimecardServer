package timecard.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ResultsSummary {

    private String carNumber;
    private Driver driver;
    private Map<String, List<Time>> layouts;
    private long total;


    public ResultsSummary(String carNumber, Driver driver) {
        this.carNumber = carNumber;
        this.driver = driver;
        layouts = new TreeMap<>();
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public long getTotal() {
        return total;
    }

    public Map<String, List<Time>> getLayouts() {
        return layouts;
    }

    public void setLayouts(Map<String, List<Time>> layouts) {
        this.layouts = layouts;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void add(Time time) {
        String layout = time.getLayout();
        layouts.putIfAbsent(layout,new ArrayList<Time>());
        List<Time> layoutTimes = layouts.get(layout);
        layoutTimes.add(time);
    }
}
