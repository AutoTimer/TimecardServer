package controllers;

import model.Result;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.FileWriterService;

@RestController
@EnableAutoConfiguration
@RequestMapping("/result")
public class ResultController {

    @RequestMapping(method= RequestMethod.POST, consumes="application/json")
    public void saveTime( @RequestBody Result result) {
        FileWriterService fileWriterService = new FileWriterService();
        fileWriterService.appendStringToFile(result);
    }


    public static void main(String[] args) throws Exception {
        SpringApplication.run(ResultController.class, args);
    }

}
