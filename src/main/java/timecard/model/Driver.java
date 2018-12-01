package timecard.model;

public class Driver {
    private String carNumber;
    private String className;
    private String name;
    private String eventType;
    private String carDescription;
    private String carCapacity;

    public Driver() {
        carNumber = "";
        className = "";
        name = "";
        eventType = "";
        carDescription = "";
        carCapacity = "";
    }

    public Driver(String line) {
        String[] fields = line.split(",");
        carNumber = fields[0];
        className = fields[1];
        name = fields[2];
        eventType = fields[3];
        carDescription = fields[4];
        carCapacity = fields[5];
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getCarDescription() {
        return carDescription;
    }

    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }

    public String getCarCapacity() {
        return carCapacity;
    }

    public void setCarCapacity(String carCapacity) {
        this.carCapacity = carCapacity;
    }

    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s",
                carNumber,
                className,
                name,
                eventType,
                carDescription,
                carCapacity);

    }
}
