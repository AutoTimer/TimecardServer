package controllers;

import model.ResultsSummary;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.TextMessagingService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@EnableAutoConfiguration
@RequestMapping("/send-text-message")
public class TextMessagingController {

    private static Map<String, String> driverDetails = new HashMap<>();

    public static void setDriverDetails() {
        driverDetails.put( "a1", "4407973229153");
        driverDetails.put("a2", "447525087465");
        driverDetails.put("a3", "447711841650");
        driverDetails.put("b1", "4407973229153");
        driverDetails.put("b2", "447525087465");
        driverDetails.put("b3", "447711841650");
        driverDetails.put("c1", "4407973229153");
        driverDetails.put("c2", "447525087465");
        driverDetails.put("c3", "447711841650");
        driverDetails.put("d1", "4407973229153");
        driverDetails.put("d2", "447525087465");
        driverDetails.put("d3", "447711841650");
    }

    @RequestMapping(method = RequestMethod.GET)
    public void sendAMessage() {
        TextMessagingService messagingService = new TextMessagingService();
        ResultsSummaryController resultsSummaryController = new ResultsSummaryController(new FileWriterService());
        List<ResultsSummary> resultsSummaryList = resultsSummaryController.calculateResultSummary();
        messagingService.sendText(driverDetails.get(resultsSummaryList.get(0).getCarNumber()));

    }
}

