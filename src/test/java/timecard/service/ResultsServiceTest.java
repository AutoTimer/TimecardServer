package timecard.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import timecard.model.Driver;
import timecard.model.Results;
import timecard.model.Time;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResultsServiceTest {

    @InjectMocks
    private ResultsService resultsService;
    @Mock
    private DriverService driverService;
    @Mock
    private EventTypeService eventTypeService;
    @Mock
    private Driver driver;

    @Test
    public void maxTimeEqualsWrongTestTime(){
        when(driverService.getDriver(any())).thenReturn(driver);
        when(eventTypeService.dropsTimes(any())).thenReturn(false);
        when(driver.getEventType()).thenReturn("NATB");
        when(driver.getClassName()).thenReturn("A1");
        List<Time> times = Arrays.asList(
                new Time("car1", "A", 1000, false, 0),
                new Time("car2", "A", 22000, false, 0)
                );
        Results results = new Results(times);

        Results actualResults = resultsService.calculateResults(results);

        assertThat(actualResults.getResultsByCompetitor().get("car2").getTotal()).isEqualTo(21000);
    }

    @Test
    public void getFastestTimeInClass(){
        when(driverService.getDriver(any())).thenReturn(driver);
        when(eventTypeService.dropsTimes(any())).thenReturn(false);
        when(driver.getEventType()).thenReturn("NATB");
        when(driver.getClassName()).thenReturn("A1").thenReturn("A1").thenReturn("A1").thenReturn("B1");
        List<Time> times = Arrays.asList(
                new Time("car1", "A", 1000, false, 0),
                new Time("car2", "A", 2000, false, 0),
                new Time("car3", "A", 500, false, 0)
                );
        Results results = new Results(times);
        long fastestTimeInClass = resultsService.getFastestTimeInClass(results, "car1", "A", 0);
        assertThat(fastestTimeInClass).isEqualTo(1000);
    }

    @Test
    public void dropTimes(){
        when(driverService.getDriver(any())).thenReturn(driver);
        when(eventTypeService.dropsTimes(any())).thenReturn(true);
        when(driver.getEventType()).thenReturn("Clubmans");
        when(driver.getClassName()).thenReturn("A1").thenReturn("A1").thenReturn("A1").thenReturn("B1");
        List<Time> times = Arrays.asList(
                new Time("car1", "A", 1000, false, 0),
                new Time("car1", "A", 2000, false, 0)
                );
        Results results = new Results(times);
        resultsService.dropTimes(results);
        assertThat(results.getResultsByCompetitor().get("car1").getLayouts().get("A").get(1).isDropped()).isTrue();
    }
}