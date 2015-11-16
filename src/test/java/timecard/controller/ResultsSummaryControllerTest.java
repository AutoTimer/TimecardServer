package timecard.controller;

import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import timecard.service.FileService;

@RunWith(MockitoJUnitRunner.class)
public class ResultsSummaryControllerTest extends TestCase {

    @Mock
    private FileService fileService;

    @Ignore
    @Test
    public void testCalculateResultSummary() throws Exception {
//        when(fileService.readResultsFromFile()).thenReturn(
//                Arrays.asList(
//                        new RawTime("a1,a,0,1000,false,0,alec"),
//                        new RawTime("b1,a,1,2000,false,0,alec"),
//                        new RawTime("a1,b,2,3000,false,0,alec")
//                )
//        );
//        ResultsSummaryController resultsSummaryController = new ResultsSummaryController(fileService);
//        EventResponse event = resultsSummaryController.getTimes();
//        assertThat(event.getResults().get .get("a1").getLayouts().get("a")).hasSize(1);
//        assertThat(event.getResultSummaries().get("a1").getLayouts().get("b")).hasSize(1);
//        assertThat(event.getResultSummaries().get("b1").getLayouts().get("a")).hasSize(1);
//        assertThat(event.getResultSummaries().get("b1").getLayouts().get("b")).hasSize(1);

    }
}