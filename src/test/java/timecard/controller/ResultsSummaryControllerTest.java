package timecard.controller;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import timecard.model.EventResponse;
import timecard.model.RawTime;
import timecard.service.DriverService;
import timecard.service.EventTypeService;
import timecard.service.TimesService;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResultsSummaryControllerTest extends TestCase {

    @Mock
    private TimesService timesService;
    @Mock
    private DriverService driverService;
    @Mock
    private EventTypeService eventTypeService;

    @Test
    public void testCalculateResultSummary() throws Exception {
        when(timesService.readResultsFromFile()).thenReturn(
                Arrays.asList(
                        new RawTime("1,1,0,49400,FALSE,0,alec"),
                        new RawTime("1,1,0,41100,FALSE,0,alec"),
                        new RawTime("1,1,0,42500,FALSE,0,alec"),
                        new RawTime("1,1,0,45700,FALSE,0,alec"),
                        new RawTime("1,1,0,45200,FALSE,0,alec"),
                        new RawTime("1,1,0,46300,FALSE,0,alec"),
                        new RawTime("1,1,0,44200,FALSE,0,alec"),
                        new RawTime("1,1,0,44700,FALSE,0,alec"),
                        new RawTime("1,1,0,43700,FALSE,0,alec"),
                        new RawTime("1,1,0,43800,FALSE,0,alec"),
                        new RawTime("1,1,0,35400,FALSE,0,alec"),
                        new RawTime("1,1,0,35700,FALSE,0,alec"),
                        new RawTime("1,1,0,35000,FALSE,0,alec"),
                        new RawTime("1,1,0,35100,FALSE,0,alec"),
                        new RawTime("1,1,0,69400,FALSE,0,alec"),
                        new RawTime("1,1,0,69000,FALSE,0,alec"),
                        new RawTime("1,1,0,69700,FALSE,0,alec")
                )
        );

        ResultsSummaryController resultsSummaryController = new ResultsSummaryController(timesService, driverService, eventTypeService);
        EventResponse event = resultsSummaryController.getTimes();
        assertThat(event.getResults().get(0).getTotalTime()).isEqualTo(795900);
    }
}