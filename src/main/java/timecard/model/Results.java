package timecard.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Results {

    private Map<String,DriverResults> resultByCompetitor;

    public Results() {
        this.resultByCompetitor = new TreeMap<>();
    }

    public Results(List<Time> times) {
        this();
        addAll(times);
        padMissingLayouts();
        padMissingTimes();
    }

    public void addAll(List<Time> times) {
        for(Time time:times){
            add(time);
        }
    }

    public Map<String, DriverResults> getResultByCompetitor() {
        return resultByCompetitor;
    }

    public List<Layout> getLayouts() {
        Map<String, Integer> maxRunsPerLayout = new TreeMap<>();
        for (DriverResults driverResults : resultByCompetitor.values()) {
            driverResults.getLayouts().forEach((layout, times) -> {
                maxRunsPerLayout.putIfAbsent(layout, 0);
                int runsInThisLayout = times.size();
                if (maxRunsPerLayout.get(layout) < runsInThisLayout) {
                    maxRunsPerLayout.put(layout, runsInThisLayout);
                }
            });
        }

        List<Layout> layouts = new ArrayList<>();
        maxRunsPerLayout.forEach((layoutName, maxNoOfRuns) -> layouts.add(new Layout(layoutName, maxNoOfRuns)));
        return layouts;
    }

    private void add(Time time) {
        String carNumber = time.getCarNumber();
        resultByCompetitor.putIfAbsent(carNumber,new DriverResults(carNumber));
        DriverResults driverResults = resultByCompetitor.get(carNumber);
        driverResults.add(time);
    }

    private void padMissingLayouts() {
        for (DriverResults driverResults : resultByCompetitor.values()) {
            for (Layout layout : getLayouts()) {
                Map<String, List<Time>> thisCarsLayouts = driverResults.getLayouts();
                thisCarsLayouts.putIfAbsent(layout.getName(), new ArrayList<>());
            }
        }
    }

    private void padMissingTimes() {
        resultByCompetitor.forEach((carNumber, competitorResults) ->
                competitorResults.getLayouts().forEach((layout, times) -> {
                    int runsInThisLayout = times.size();
                    int noOfRunsToPad = getMaxRunsInThisLayout(layout, getLayouts()) - runsInThisLayout;
                    for (int i = 0; i < noOfRunsToPad; i++) {
                        times.add(new Time(carNumber, layout));
                    }
                }));
    }

    private int getMaxRunsInThisLayout(String layoutName, List<Layout> layouts) {
        int maxRunsInThisLayout = 0;
        for (Layout layout : layouts) {
            if (layoutName.equals(layout.getName())) {
                maxRunsInThisLayout = layout.getNoOfRuns();
            }
        }
        return maxRunsInThisLayout;
    }
}
