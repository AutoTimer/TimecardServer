package timecard.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import timecard.service.DriverService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.signum;

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
