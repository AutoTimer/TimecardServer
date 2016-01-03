package timecard.model;

public class Time {
    private static final long TIME_PER_PENALTY = 5000;

    private String carNumber;
    private long elapsedTimeWithPenalties;
    private String layout;
    private boolean wrongTest;
    private int penalties;
    private boolean dropped;

    public Time(String layout, long elapsedTimeWithPenalties, boolean wrongTest, int penalties) {
        this.layout = layout;
        this.elapsedTimeWithPenalties = elapsedTimeWithPenalties;
        this.wrongTest = wrongTest;
        this.penalties = penalties;
    }

    public Time(RawTime rawTime) {
        this.carNumber = rawTime.getCarNumber();
        this.layout = rawTime.getLayout();
        this.wrongTest = rawTime.isWrongTest();
        this.penalties = rawTime.getPenalty();
        this.elapsedTimeWithPenalties = rawTime.getEndTime() - rawTime.getStartTime() + rawTime.getPenalty() * TIME_PER_PENALTY;
    }

    public Time(String carNumber, String layout) {
        this.carNumber = carNumber;
        this.layout = layout;
    }

    public Time() {
        this(null,0,false,0);
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public long getElapsedTimeWithPenalties() {
        return elapsedTimeWithPenalties;
    }

    public void setElapsedTimeWithPenalties(long elapsedTimeWithPenalties) {
        this.elapsedTimeWithPenalties = elapsedTimeWithPenalties;
    }

    public boolean isWrongTest() {
        return wrongTest;
    }

    public void setWrongTest(boolean wrongTest) {
        this.wrongTest = wrongTest;
    }

    public void setPenalties(int penalties) {
        this.penalties = penalties;
    }

    public int getPenalties() {
        return penalties;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    @Override
    public String toString() {
        return "Time{" +
                "carNumber='" + carNumber + '\'' +
                ", elapsedTimeWithPenalties=" + elapsedTimeWithPenalties +
                ", layout=" + layout +
                ", wrongTest=" + wrongTest +
                ", penalties=" + penalties +
                '}';
    }

    public void setDropped(boolean dropped) {
        this.dropped = dropped;
    }

    public boolean isDropped() {
        return dropped;
    }
}
