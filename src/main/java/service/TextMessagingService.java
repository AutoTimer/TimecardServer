package service;


import com.clockworksms.ClockWorkSmsService;
import com.clockworksms.ClockworkException;
import com.clockworksms.ClockworkSmsResult;
import com.clockworksms.SMS;

public class TextMessagingService {

    public void sendText(String phoneNumber) {
        try {
            ClockWorkSmsService clockWorkSmsService = new ClockWorkSmsService("f9eb61280ddcee375976ea88325d1996985f2cb2");
            SMS sms = new SMS(phoneNumber, "We have a winner and it is you!! Congratulations on winning the race! We hope to see you in the next race!");
            ClockworkSmsResult result = clockWorkSmsService.send(sms);

            if (result.isSuccess()) {
                System.out.println("Sent with ID: " + result.getId());
            } else {
                System.out.println("Error: " + result.getErrorMessage());
            }
        } catch (ClockworkException e) {
            e.printStackTrace();
        }
    }
}