package timecard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import timecard.model.Event;
import timecard.model.ResultsSummary;
import timecard.model.Time;
import timecard.responses.EventResponse;
import timecard.responses.ResultSummaryResponse;
import timecard.service.DriverService;
import timecard.service.TimeService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/results-summary")
public class ResultsSummaryController {
    private final EventService eventService;
    private final TimeService timesService;
    private final DriverService driverService;

    @Autowired
    public ResultsSummaryController(EventService eventService, TimeService timesService, DriverService driverService) {
        this.eventService = eventService;
        this.timesService = timesService;
        this.driverService = driverService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public EventResponse getTimes() {
        Event event = new Event(timesService.getTimes());

        eventService.calculateResults(event);

        return buildEventResponse(event);
    }

    private EventResponse buildEventResponse(Event event) {
        List<ResultSummaryResponse> results = new ArrayList<>();
        for (ResultsSummary summary : event.getResultSummariesByCar().values()) {
            List<Time> timesToReturn = new ArrayList<>();
            summary.getLayouts().values().forEach(timesToReturn::addAll);
            results.add(new ResultSummaryResponse(summary.getCarNumber(), driverService.getDriver(summary.getCarNumber()), timesToReturn, summary.getTotal()));
        }

        results.sort(ResultSummaryResponse::compareByEventTypeClassAndTime);
        return new EventResponse(event.getLayouts(), results);
    }
}
