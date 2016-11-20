package timecard.responses;

import timecard.model.Layout;

import java.util.ArrayList;
import java.util.List;

public class EventResponse {

    private List<Layout> layouts;
    private List<ResultSummaryResponse> results;

    public EventResponse(){
        results = new ArrayList<>();
        layouts = new ArrayList<>();
    }

    public EventResponse(List<Layout> layouts, List<ResultSummaryResponse> results) {
        this.layouts = layouts;
        this.results = results;
    }

    public List<Layout> getLayouts() {
        return layouts;
    }

    public void setLayouts(List<Layout> layouts) {
        this.layouts = layouts;
    }

    public List<ResultSummaryResponse> getResults() {
        return results;
    }

    public void setResults(List<ResultSummaryResponse> results) {
        this.results = results;
    }
}
