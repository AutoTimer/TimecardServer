package timecard.model;

import java.util.ArrayList;
import java.util.List;

public class EventResponse {

    private List<LayoutResponse> layouts;
    private List<ResultSummaryResponse> results;

    public EventResponse(){
        results = new ArrayList<>();
        layouts = new ArrayList<>();
    }

    public EventResponse(List<LayoutResponse> layouts, List<ResultSummaryResponse> results) {
        this.layouts = layouts;
        this.results = results;
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
