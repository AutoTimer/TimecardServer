package timecard.model;

public class EventType {
    private String name;
    private boolean dropsTimes;

    public EventType(String line) {
        String[] fields = line.split(",");
        this.name = fields[0];
        this.dropsTimes = Boolean.parseBoolean(fields[1]);
    }

    public boolean isDropsTimes() {
        return dropsTimes;
    }

    public void setDropsTimes(boolean dropsTimes) {
        this.dropsTimes = dropsTimes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return String.format("%s,%s", name, dropsTimes);
    }
}
