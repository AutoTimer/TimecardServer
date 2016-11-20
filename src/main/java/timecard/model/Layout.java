package timecard.model;

public class Layout {
    private String name;
    private int noOfRuns;

    public Layout(String name, int noOfRuns) {
        this.name = name;
        this.noOfRuns = noOfRuns;
    }

    public int getNoOfRuns() {
        return noOfRuns;
    }

    public void setNoOfRuns(int noOfRuns) {
        this.noOfRuns = noOfRuns;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
