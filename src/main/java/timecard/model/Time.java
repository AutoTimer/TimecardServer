package timecard.model;

public class Time {
    private static final long TIME_PER_PENALTY = 5000;

    private String className;
    private String carNumber;
    private long elapsedTimeWithPenalties;
    private String layout;
    private boolean wrongTest;
    private boolean penaltiesApplied;

    public Time(String layout, long elapsedTimeWithPenalties, boolean wrongTest, boolean penaltiesApplied) {
        this.layout = layout;
        this.elapsedTimeWithPenalties = elapsedTimeWithPenalties;
        this.wrongTest = wrongTest;
        this.penaltiesApplied = penaltiesApplied;
    }

    public Time(RawTime rawTime, String className) {
        this.carNumber = rawTime.getCarNumber();
        this.className = className;
        this.layout = rawTime.getLayout();
        this.wrongTest = rawTime.isWrongTest();
        this.penaltiesApplied = rawTime.getPenalty()!=0;
        this.elapsedTimeWithPenalties = rawTime.getEndTime() - rawTime.getStartTime() + rawTime.getPenalty() * TIME_PER_PENALTY;
    }

    public Time(String carNumber, String layout) {
        this.carNumber = carNumber;
        this.layout = layout;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public boolean getPenaltiesApplied() {
        return penaltiesApplied;
    }

    public void setPenaltiesApplied(boolean penaltiesApplied) {
        this.penaltiesApplied = penaltiesApplied;
    }

    public boolean isPenaltiesApplied() {
        return penaltiesApplied;
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
                ", penaltiesApplied=" + penaltiesApplied +
                '}';
    }
}
