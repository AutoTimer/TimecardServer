package timecard.model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.signum;

public class EventResponse {
    private List<ResultSummaryResponse> results = new ArrayList<>();
    private List<LayoutResponse> layouts = new ArrayList<>();

    public EventResponse(Event event, List<LayoutResponse> layouts) {
        for(ResultsSummary summary:event.getResultSummaries().values()){
            List<Time> timesToReturn = new ArrayList<>();
            for(List<Time> times:summary.getLayouts().values()){
                timesToReturn.addAll(times);
            }
            results.add(new ResultSummaryResponse(summary.getCarNumber(), summary.getDriver(), timesToReturn, summary.getTotal()));
        }

        results.sort((summary1, summary2) ->
                summary1.getDriver().getClassName().equals(summary2.getDriver().getClassName()) ?
                signum(summary1.getTotalTime()-summary2.getTotalTime()) : summary1.getDriver().getClassName().compareTo(summary2.getDriver().getClassName()));

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
