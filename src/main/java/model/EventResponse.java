package model;

import java.util.ArrayList;
import java.util.List;

public class EventResponse {
    private List<ResultSummaryResponse> results = new ArrayList<>();
    private List<LayoutResponse> layouts = new ArrayList<>();

    public EventResponse(Event event, List<LayoutResponse> layouts) {
        for(ResultsSummary summary:event.getResultSummaries().values()){
            List<Time> timesToReturn = new ArrayList<>();
            for(List<Time> times:summary.getLayouts().values()){
                timesToReturn.addAll(times);
            }
            results.add(new ResultSummaryResponse(summary.getCarNumber(), timesToReturn, summary.getTotal()));
        }
        this.layouts = layouts;
    }

    public List<LayoutResponse> getLayouts() {
        return layouts;
    }

    public void setLayouts(List<LayoutResponse> layouts) {
        this.layouts = layouts;
    }

    public List<ResultSummaryResponse> getResults() {
        return results;
    }

    public void setResults(List<ResultSummaryResponse> results) {
        this.results = results;
    }
}
