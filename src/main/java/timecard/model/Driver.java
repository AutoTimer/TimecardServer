package timecard.model;

public class Driver {
    private String carNumber;
    private String className;

    //    private int capacity;
//    private String carDescription;
//    private String name;
//    private String eventType;

    public Driver() {
        carNumber = "";
        className = "";
    }

    public Driver(String line) {
        String[] fields = line.split(",");
        carNumber = fields[0];
        className = fields[1];
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String toString(){
        return String.format("%s,%s",carNumber,className);
    }
}
