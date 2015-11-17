package timecard.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Event {

    private Map<String,ResultsSummary> resultSummaries;

    public Event() {
        this.resultSummaries = new TreeMap<>();
    }

    public void addAll(List<Time> times) {
        for(Time time:times){
            add(time);
        }
    }

    public Map<String, ResultsSummary> getResultSummaries() {
        return resultSummaries;
    }

    public void setResultSummaries(Map<String, ResultsSummary> resultSummaries) {
        this.resultSummaries = resultSummaries;
    }

    private void add(Time time) {
        String carNumber = time.getCarNumber();
        resultSummaries.putIfAbsent(carNumber,new ResultsSummary(carNumber));
        ResultsSummary resultsSummary = resultSummaries.get(carNumber);
        resultsSummary.add(time);
    }
}
