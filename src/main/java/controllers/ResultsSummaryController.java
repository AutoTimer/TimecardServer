package controllers;

import model.Result;
import model.ResultsSummary;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
        List<ResultsSummary> resultsSummary1 = new ArrayList<>(new LinkedHashSet<>(resultsSummary));

        for(Result res : modifiableResults){
            resultsSummary1.add(new ResultsSummary(res.getCarNumber(), getAListOfTimeTaken(modifiableResults, res.getCarNumber()), calculateTotal(modifiableResults, res.getCarNumber())));
        }
        return resultsSummary1;
    }


    private long calculateTotal(List<Result> result, String carNumber) {
        long total = 0;
        for(int i =0; i < result.size(); i++) {
            if(result.get(i).getCarNumber().equals(carNumber)) {
                total+= (result.get(i).getEndTime() - result.get(i).getStartTime());
            }
        }
        return total;
    }

    private List<Long> getAListOfTimeTaken(List<Result> result, String carNumber) {
        List<Long> timeTaken = new ArrayList<>();
        for(int i =0; i < result.size(); i++) {
            if(result.get(i).getCarNumber().equals(carNumber)) {
                timeTaken.add(result.get(i).getEndTime() - result.get(i).getStartTime());

            }
        }
        return timeTaken;
    }
}
