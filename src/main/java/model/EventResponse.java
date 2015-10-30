package model;

import java.util.ArrayList;
import java.util.List;

public class EventResponse {
    private List<ResultSummaryResponse> results = new ArrayList<>();

    public EventResponse(Event event) {
        for(ResultsSummary summary:event.getResultSummaries().values()){
            List<Time> timesToReturn = new ArrayList<>();
            for(List<Time> times:summary.getLayouts().values()){
                timesToReturn.addAll(times);
            }
            results.add(new ResultSummaryResponse(summary.getCarNumber(), timesToReturn, summary.getTotal()));
        }
    }

    public List<ResultSummaryResponse> getResults() {
        return results;
    }

    public void setResults(List<ResultSummaryResponse> results) {
        this.results = results;
    }
}
