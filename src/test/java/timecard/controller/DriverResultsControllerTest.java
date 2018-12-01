package timecard.controller;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import timecard.model.Driver;
import timecard.model.RawTime;
import timecard.responses.ResultsResponse;
import timecard.service.*;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DriverResultsControllerTest extends TestCase {

    @Mock
    private FileService fileService;
    @Mock
    private DriverService driverService;
    @Mock
    private EventTypeService eventTypeService;
    @Mock
    private Driver driver;

    @Test
    public void testCalculateResultSummary() throws Exception {
        when(driverService.getDriver(any())).thenReturn(driver);
        when(driver.getEventType()).thenReturn("NATB");
        when(driver.getClassName()).thenReturn("A1");
        when(fileService.readEntitiesFromFile(RawTime.class)).thenReturn(
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

        ResultsController resultsController = new ResultsController(new ResultsService(driverService, eventTypeService), new TimeService(fileService), driverService);
        ResultsResponse event = resultsController.getTimes();
        assertThat(event.getResults().get(0).getTotalTime()).isEqualTo(795900);
    }
}