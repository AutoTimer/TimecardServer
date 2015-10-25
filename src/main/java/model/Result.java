package model;


public class Result {

    private String carNumber;
    private int layout;
    private long startTime;
    private long endTime;
    private boolean wrongTest;
    private int penalty;
    private String marshalName;

    public Result() {
    }

    public Result(String carNumber, int layout, long startTime, long endTime, boolean wrongTest, int penalty) {
        this.carNumber = carNumber;
        this.layout = layout;
        this.startTime = startTime;
        this.endTime = endTime;
        this.wrongTest = wrongTest;
        this.penalty = penalty;
    }

    public Result(String lineFromFile) {
        String[] lines = lineFromFile.split(",");
        carNumber=lines[0];
        layout=Integer.parseInt(lines[1]);
        startTime=Long.parseLong(lines[2]);
        endTime=Long.parseLong(lines[3]);
        wrongTest=Boolean.parseBoolean(lines[4]);
        penalty=Integer.parseInt(lines[5]);
        marshalName=lines[6];

    }

    public String getMarshalName() {
        return marshalName;
    }

    public void setMarshalName(String marshalName) {
        this.marshalName = marshalName;
    }


    public String getCarNumber() {
        return carNumber;
    }

    public int getLayout() {
        return layout;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public boolean isWrongTest() {
        return wrongTest;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }


    public void setWrongTest(boolean wrongTest) {
        this.wrongTest = wrongTest;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    @Override
    public String toString() {
        return String.format("%s,%d,%d,%d,%b,%d,%s", carNumber, layout, startTime, endTime, wrongTest, penalty, marshalName);
    }
}
