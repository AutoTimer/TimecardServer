package model;


public class ResultsSummary {

    private String carNumber;
    private long timeTaken;
    private int layout;
    private int total;

    public void ResultsSummary() {

    }

    public ResultsSummary(String carNumber, long timeTaken, int layout){
// , int total, int subTotal, int position, int combinedOverall, int awards) {
        this.carNumber = carNumber;
        this.timeTaken = timeTaken;
        this.layout = layout;
//        this.total = total;
//        this.subTotal = subTotal;
//        this.position = position;
//        this.combinedOverall = combinedOverall;
//        this.awards = awards;
    }

    public ResultsSummary() {
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setCombinedOverall(int combinedOverall) {
        this.combinedOverall = combinedOverall;
    }

    public void setAwards(int awards) {
        this.awards = awards;
    }

    private int subTotal;
    private int position;

    public int getLayout() {
        return layout;
    }

    public int getTotal() {
        return total;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public int getPosition() {
        return position;
    }

    public int getCombinedOverall() {
        return combinedOverall;
    }

    public int getAwards() {
        return awards;
    }

    private int combinedOverall;
    private int awards;


    public String getCarNumber() {
        return carNumber;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

}
