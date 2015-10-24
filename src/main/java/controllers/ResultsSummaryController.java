package controllers;

import model.Result;
import model.ResultsSummary;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/results-summary")
public class ResultsSummaryController {

    @RequestMapping(method= RequestMethod.GET)
    public List<ResultsSummary> getTime() {
        List<Result> modifiableResults = new ArrayList<>(ResultController.results);

        Collections.sort(modifiableResults, new Comparator<Result>() {

            public int compare(Result r1, Result r2) {
                if(r1.getLayout() > r2.getLayout()) {
                    return 1;
                } else if(r1.getLayout() == r2.getLayout()){
                    return (r1.getStartTime() > r2.getStartTime())? 1 : -1;
                }else {
                    return -1;
                }

            }
        });

        List<Result> modifiableResultSummary = new ArrayList<>(modifiableResults);
        Collections.sort(modifiableResultSummary, new Comparator<Result>() {

            public int compare(Result r1, Result r2) {
                return (r1.getEndTime() - r1.getStartTime() < r2.getEndTime() - r2.getStartTime())? -1 : 1;
            }
        });

        List<ResultsSummary> resultsSummary = new ArrayList<>();
        for(Result res : modifiableResults){
            resultsSummary.add(new ResultsSummary(res.getCarNumber(), res.getEndTime() - res.getStartTime() , res.getLayout()));
        }
        return resultsSummary;
    }

//    @RequestMapping(method= RequestMethod.GET)
//    public List<Result> getTime() {
//        List<Result> modifiableResult = new ArrayList<>(ResultController.results);
//
//        Collections.sort(modifiableResult, new Comparator<Result>() {
//
//            public int compare(Result r1, Result r2) {
//                return r1.getEndTime() - r1.getStartTime() > r2.getEndTime() - r2.getStartTime() ? 1 : -1;
//            }
//        });
//
//        List<ResultsSummary> resultsSummaries = new ArrayList<>();
//        for(Result res : modifiableResult){
//
//        }
//        return modifiableResult;
//    }

}
