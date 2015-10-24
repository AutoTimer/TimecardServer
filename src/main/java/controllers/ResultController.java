package controllers;

import model.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ResultController {

    @RequestMapping(value="/result", method= RequestMethod.POST)
    public List<Result> saveTime( @RequestBody Result result) {
        List<Result> results = new ArrayList<>();
        results.add(result);
        return results;
    }


}
