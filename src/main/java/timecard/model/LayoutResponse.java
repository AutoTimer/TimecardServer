package timecard.model;

public class LayoutResponse {
    private String name;
    private int noOfRuns;

    public LayoutResponse(String name, int noOfRuns) {
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
