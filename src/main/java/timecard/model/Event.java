package timecard.model;

import java.util.*;

public class Event {

    private Map<String,ResultsSummary> resultSummariesByCar;

    public Event() {
        this.resultSummariesByCar = new TreeMap<>();
    }

    public Event(List<Time> times) {
        this();
        addAll(times);
    }

    public void addAll(List<Time> times) {
        for(Time time:times){
            add(time);
        }
    }

    public Map<String, ResultsSummary> getResultSummariesByCar() {
        return resultSummariesByCar;
    }

    public void setResultSummariesByCar(Map<String, ResultsSummary> resultSummariesByCar) {
        this.resultSummariesByCar = resultSummariesByCar;
    }

    public List<Layout> getLayouts() {
        Map<String, Integer> maxRunsPerLayout = new TreeMap<>();
        for (ResultsSummary resultsSummary : resultSummariesByCar.values()) {
            for (Map.Entry<String, List<Time>> layout : resultsSummary.getLayouts().entrySet()) {
                maxRunsPerLayout.putIfAbsent(layout.getKey(), 0);
                int runsInThisLayout = layout.getValue().size();
                if (maxRunsPerLayout.get(layout.getKey()) < runsInThisLayout) {
                    maxRunsPerLayout.put(layout.getKey(), runsInThisLayout);
                }
            }
        }

        List<Layout> layouts = new ArrayList<>();
        maxRunsPerLayout.forEach((layoutName, maxNoOfRuns) -> layouts.add(new Layout(layoutName, maxNoOfRuns)));
        return layouts;
    }

    private void add(Time time) {
        String carNumber = time.getCarNumber();
        resultSummariesByCar.putIfAbsent(carNumber,new ResultsSummary(carNumber));
        ResultsSummary resultsSummary = resultSummariesByCar.get(carNumber);
        resultsSummary.add(time);
    }
}
