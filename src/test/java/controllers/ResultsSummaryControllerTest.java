package controllers;

import junit.framework.TestCase;
import model.Event;
import model.RawTime;
import model.ResultsSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class ResultsSummaryControllerTest extends TestCase {

    @Mock
    private FileWriterService fileWriterService;

    @Test
    public void testCalculateResultSummary() throws Exception {
        when(fileWriterService.readFromFile()).thenReturn(
                Arrays.asList(
                        new RawTime("a1,a,0,1000,false,0,alec"),
                        new RawTime("b1,a,1,2000,false,0,alec"),
                        new RawTime("a1,b,2,3000,false,0,alec")
                )
        );
        ResultsSummaryController resultsSummaryController = new ResultsSummaryController(fileWriterService);
        Event event = resultsSummaryController.calculateResultSummary();
        assertThat(event.getResultSummaries().get("a1").getLayouts().get("a")).hasSize(1);
        assertThat(event.getResultSummaries().get("a1").getLayouts().get("b")).hasSize(1);
        assertThat(event.getResultSummaries().get("b1").getLayouts().get("a")).hasSize(1);
        assertThat(event.getResultSummaries().get("b1").getLayouts().get("b")).hasSize(1);

    }
}