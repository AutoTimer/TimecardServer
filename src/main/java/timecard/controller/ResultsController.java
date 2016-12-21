package timecard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import timecard.model.Results;
import timecard.model.Time;
import timecard.responses.DriverResultsResponse;
import timecard.responses.ResultsResponse;
import timecard.service.DriverService;
import timecard.service.TimeService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/results-summary")
public class ResultsController {
    private final ResultsService resultsService;
    private final TimeService timesService;
    private final DriverService driverService;

    @Autowired
    public ResultsController(ResultsService resultsService, TimeService timesService, DriverService driverService) {
        this.resultsService = resultsService;
        this.timesService = timesService;
        this.driverService = driverService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResultsResponse getTimes() {
        Results results = new Results(timesService.getTimes());

        resultsService.calculateResults(results);

        return buildResultsResponse(results);
    }

    private ResultsResponse buildResultsResponse(Results results) {
        List<DriverResultsResponse> driverResultsResponses = new ArrayList<>();
        results.getResultByCompetitor().values().forEach(summary -> {
            List<Time> timesToReturn = new ArrayList<>();
            summary.getLayouts().values().forEach(timesToReturn::addAll);
            driverResultsResponses.add(
                    new DriverResultsResponse(
                            summary.getCarNumber(),
                            driverService.getDriver(summary.getCarNumber()),
                            timesToReturn,
                            summary.getTotal())
            );
        });

        driverResultsResponses.sort(DriverResultsResponse::compareByEventTypeClassAndTime);
        return new ResultsResponse(results.getLayouts(), driverResultsResponses);
    }
}
