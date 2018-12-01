package timecard.responses;

import timecard.model.Layout;

import java.util.ArrayList;
import java.util.List;

public class ResultsResponse {

    private List<Layout> layouts;
    private List<DriverResultsResponse> results;

    public ResultsResponse(){
        results = new ArrayList<>();
        layouts = new ArrayList<>();
    }

    public ResultsResponse(List<Layout> layouts, List<DriverResultsResponse> results) {
        this.layouts = layouts;
        this.results = results;
    }

    public List<Layout> getLayouts() {
        return layouts;
    }

    public void setLayouts(List<Layout> layouts) {
        this.layouts = layouts;
    }

    public List<DriverResultsResponse> getResults() {
        return results;
    }

    public void setResults(List<DriverResultsResponse> results) {
        this.results = results;
    }
}
